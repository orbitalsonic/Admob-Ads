package com.hypersoft.ads.practice.startup.ui

import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.hypersoft.ads.practice.R
import com.hypersoft.ads.practice.base.fragment.BaseFragment
import com.hypersoft.ads.practice.core.extensions.collectWhenCreated
import com.hypersoft.ads.practice.core.extensions.collectWhenStarted
import com.hypersoft.ads.practice.core.extensions.launchWhenResumed
import com.hypersoft.ads.practice.databinding.FragmentStartupBinding
import com.hypersoft.ads.practice.startup.effect.StartupEffect
import com.hypersoft.ads.practice.startup.intent.StartupIntent
import com.hypersoft.ads.practice.startup.state.StartupState
import com.hypersoft.ads.practice.startup.viewModel.StartupViewModel
import com.hypersoft.ads.practice.gmaAds.appOpen.AppOpenAdKey
import com.hypersoft.ads.practice.gmaAds.common.extensions.blockAppOpen
import com.hypersoft.ads.practice.gmaAds.common.extensions.loadAppOpenAd
import com.hypersoft.ads.practice.gmaAds.common.extensions.loadInterstitialAd
import com.hypersoft.ads.practice.gmaAds.common.extensions.loadNativeAd
import com.hypersoft.ads.practice.gmaAds.common.extensions.showAppOpenOrInterstitialAd
import com.hypersoft.ads.practice.gmaAds.consent.ConsentListener
import com.hypersoft.ads.practice.gmaAds.consent.ConsentManager
import com.hypersoft.ads.practice.gmaAds.interstitial.InterstitialAdKey
import com.hypersoft.ads.practice.gmaAds.nativeAd.NativeAdKey
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class StartupFragment : BaseFragment<FragmentStartupBinding>(FragmentStartupBinding::inflate) {

    private val viewModel: StartupViewModel by viewModel()

    private var consentManager: ConsentManager? = null

    override fun onViewCreated() {
        blockAppOpen()
        viewModel.handleIntent(StartupIntent.ScreenStarted)
    }

    override fun initObservers() {
        collectWhenStarted(viewModel.state, ::renderState)
        collectWhenCreated(viewModel.effect, ::handleEffect)
    }

    private fun renderState(state: StartupState) {
        binding.cpiLoadingStartup.isVisible = state.showLoading
    }

    private fun handleEffect(effect: StartupEffect) {
        when (effect) {
            StartupEffect.InitializeConsent -> initializeConsent()
            StartupEffect.LoadAds -> loadAds()
            StartupEffect.NavigateToLanguage -> leaveStartup { findNavController().navigate(R.id.action_startupFragment_to_languageFragment) }
            StartupEffect.NavigateToOnboarding -> leaveStartup { findNavController().navigate(R.id.action_startupFragment_to_onboardingFragment) }
            StartupEffect.NavigateToMenu -> leaveStartup { findNavController().navigate(R.id.action_startupFragment_to_menuFragment) }
            StartupEffect.NavigateToDashboard -> findNavController().navigate(R.id.action_startupFragment_to_dashboardFragment)
        }
    }

    private fun initializeConsent() {
        val hostActivity = activity ?: return
        val manager = ConsentManager(hostActivity)
        consentManager = manager
        manager.start(
            listener = object : ConsentListener {
                override fun onConsentFormReady() {
                    launchWhenResumed {
                        manager.showForm()
                        viewModel.handleIntent(StartupIntent.ConsentFormShown)
                    }
                }

                override fun onAdsAllowed(canRequestAds: Boolean) {
                    viewModel.handleIntent(StartupIntent.AdsCanBeLoaded)
                }
            },
        )
    }

    private fun loadAds() {
        var remaining = 2
        val onSettled = {
            remaining -= 1
            if (remaining == 0) viewModel.handleIntent(StartupIntent.AdsLoaded)
        }
        loadAppOpenAd(AppOpenAdKey.STARTUP) { onSettled() }
        loadInterstitialAd(InterstitialAdKey.STARTUP) { onSettled() }
        preloadFunnelAds()
    }

    private fun leaveStartup(onDone: () -> Unit) {
        if (viewModel.state.value.skipAds) {
            onDone()
        } else {
            showAppOpenOrInterstitialAd(AppOpenAdKey.STARTUP, InterstitialAdKey.STARTUP, onDone)
        }
    }

    private fun preloadFunnelAds() {
        viewLifecycleOwner.lifecycleScope.launch {
            if (!sharedPrefRepository.isFirstTimeUser()) return@launch
            if (sharedPrefRepository.isLanguageSelected()) return@launch
            loadNativeAd(NativeAdKey.LANGUAGE)
        }
    }

    override fun onDestroyView() {
        consentManager?.reset()
        consentManager = null
        super.onDestroyView()
    }
}