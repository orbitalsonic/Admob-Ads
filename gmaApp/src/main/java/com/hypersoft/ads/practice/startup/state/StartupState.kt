package com.hypersoft.ads.practice.startup.state

data class StartupState(
    val isLoading: Boolean = true,
    val isConsentTimerRunning: Boolean = false,
    val isAdsTimerRunning: Boolean = false,
    val hasResolvedDestination: Boolean = false,
    val skipAds: Boolean = false,
) {
    val showLoading: Boolean = isLoading
}