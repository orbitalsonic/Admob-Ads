plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.google.services)
    alias(libs.plugins.navigation.safeargs)
}

val admobIds = mapOf(
    "admob_inter_ids" to "ca-app-pub-3940256099942544/1033173712",
    "admob_rewarded_ids" to "ca-app-pub-3940256099942544/5224354917",
    "admob_rewarded_inter_ids" to "ca-app-pub-3940256099942544/5354046379",
    "admob_native_ids" to "ca-app-pub-3940256099942544/2247696110",
    "admob_banner_ids" to "ca-app-pub-3940256099942544/2014213617",
    "admob_app_open_ids" to "ca-app-pub-3940256099942544/9257395921",
    "admob_app_id" to "ca-app-pub-3940256099942544~3347511713",
)

android {
    namespace = "com.orbitalsonic.admobads"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.orbitalsonic.admobads"
        minSdk = 24
        targetSdk = 36
        versionCode = 109
        versionName = "4.7.109"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            admobIds.forEach { (name, value) -> resValue("string", name, "\"$value\"") }
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        debug {
            admobIds.forEach { (name, value) -> resValue("string", name, "\"$value\"") }
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
        resValues = true
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
    }
}

dependencies {
    // Core libraries
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)

    // SDP layout size && SSP Text sizes
    implementation(libs.sdp.android)
    implementation(libs.ssp.android)

    // Firebase configuration
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.config)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)

    // Navigation components
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // ViewModel and LiveData
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.process)

    // WorkManager
    implementation(libs.androidx.work.runtime.ktx)

    // AdMob ads
    implementation(libs.play.services.ads)
}
