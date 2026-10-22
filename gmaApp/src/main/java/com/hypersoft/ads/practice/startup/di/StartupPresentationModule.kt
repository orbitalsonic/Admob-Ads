package com.hypersoft.ads.practice.startup.di

import com.hypersoft.ads.practice.startup.viewModel.StartupViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.lazyModule

val startupPresentationModule = lazyModule {

    //// ViewModels
    viewModel {
        StartupViewModel(
            remoteConfigRepository = get(),
            sharedPrefRepository = get(),
        )
    }
}