package com.batzalcancia.itunes.plugin.configs

import com.android.build.gradle.BaseExtension

/**
 * Configures android version and flavor
 */
internal fun BaseExtension.configureAndroid() {
    compileSdkVersion(Properties.COMPILE_SDK_VERSION)
    buildToolsVersion(Properties.BUILD_TOOLS_VERSION)

    defaultConfig {
        minSdkVersion(Properties.MIN_SDK_VERSION)
        targetSdkVersion(Properties.TARGET_SDK_VERSION)
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    flavorDimensions("environment")

    viewBinding.isEnabled = true
}
