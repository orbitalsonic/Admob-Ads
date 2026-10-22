buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        // Overrides the Kotlin version bundled with AGP's built-in Kotlin support.
        classpath(libs.kotlin.gradle.plugin)
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.navigation.safeargs) apply false
}
