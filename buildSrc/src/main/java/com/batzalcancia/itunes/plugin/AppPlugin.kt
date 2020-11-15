package com.batzalcancia.itunes.plugin

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import com.batzalcancia.itunes.plugin.configs.configureAndroid
import org.gradle.api.JavaVersion
import org.gradle.kotlin.dsl.getByType
import com.batzalcancia.itunes.plugin.configs.configureDependencies

open class AppPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        val appExtension = target.extensions.getByType<AppExtension>()

        appExtension.configureApplication()

        target.configureDependencies()
    }

    /**
     * Configures buildTypes, productFlavors and compileOptions
     */
    private fun AppExtension.configureApplication() {
        configureAndroid()

        buildTypes {
            getByName("debug") {
                applicationIdSuffix = ".debug"
                versionNameSuffix = "+debug"
            }
        }

        productFlavors {
            create("dev") {
                dimension = "environment"
                applicationIdSuffix = ".dev"
            }
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }

    }

}