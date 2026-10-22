package com.hypersoft.ads.practice.startup.intent

sealed class StartupIntent {
    data object ScreenStarted : StartupIntent()
    data object ConsentFormShown : StartupIntent()
    data object AdsCanBeLoaded : StartupIntent()
    data object AdsLoaded : StartupIntent()
    data object ConsentTimerExpired : StartupIntent()
    data object AdsTimerExpired : StartupIntent()
}