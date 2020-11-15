plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    id("com.batzalcancia.itunes.app")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    defaultConfig {
        applicationId = "com.batzalcancia.itunes"
        versionName = "1.0.0"
    }

    productFlavors {
        getByName("dev") {
            dimension = "environment"
            applicationId = "com.batzalcancia.itunes"
            versionCode = 1
            versionNameSuffix = "-dev+1"
        }
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    packagingOptions {
        exclude("META-INF/*.kotlin_module")
    }
}

dependencies {
    implementation(project("::core"))
    implementation(project("::tracks"))
}