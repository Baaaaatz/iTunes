package com.batzalcancia.itunes.plugin

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import com.batzalcancia.itunes.plugin.configs.configureAndroid
import com.batzalcancia.itunes.plugin.configs.configureDependencies
import org.gradle.api.JavaVersion

open class LibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val libraryExtension = target.extensions.getByType<LibraryExtension>()

        libraryExtension.configureLibrary()

        target.configureDependencies()
    }

    /**
     * Configures buildTypes, productFlavors and compileOptions
     */
    private fun LibraryExtension.configureLibrary() {
        configureAndroid()

        buildTypes {
            getByName("release") {
                isMinifyEnabled = true
                consumerProguardFiles("consumer-rules.pro")
            }
        }

        productFlavors {
            create("dev")
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }

    }

}