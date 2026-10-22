package com.hypersoft.ads.practice.startup.effect

sealed class StartupEffect {
    data object InitializeConsent : StartupEffect()
    data object LoadAds : StartupEffect()
    data object NavigateToLanguage : StartupEffect()
    data object NavigateToOnboarding : StartupEffect()
    data object NavigateToMenu : StartupEffect()
    data object NavigateToDashboard : StartupEffect()
}