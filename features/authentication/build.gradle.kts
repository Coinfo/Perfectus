/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

import dependencies.Dependencies
import dependencies.AnnotationProcessorsDependencies

plugins {
    id("commons.android-library")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdkVersion(BuildAndroidConfig.COMPILE_SDK_VERSION)
    buildToolsVersion(BuildAndroidConfig.BUILD_TOOLS_VERSION)

    defaultConfig {
        minSdkVersion(BuildAndroidConfig.MIN_SDK_VERSION)
        targetSdkVersion(BuildAndroidConfig.TARGET_SDK_VERSION)
    }

    buildFeatures {
        dataBinding = true
    }

    resourcePrefix = "authentication_"
}

dependencies {
    implementation(project(BuildModules.Libraries.AUTHENTICATOR))
    implementation(project(BuildModules.Libraries.LOGGER))
    implementation(project(BuildModules.Libraries.DESIGN))

    implementation(Dependencies.KOTLIN)
    implementation(Dependencies.CORE_KTX)
    implementation(Dependencies.NAVIGATION_FRAGMENT_KTX)
    implementation(Dependencies.NAVIGATION_UI_KTX)
    implementation(Dependencies.CONSTRAINT_LAYOUT)
    implementation(Dependencies.CARD_VIEW)

    implementation(Dependencies.HILT_ANDROID)
    implementation("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha02")

    kapt(AnnotationProcessorsDependencies.HILT_ANDROID_COMPILER)
    kapt(AnnotationProcessorsDependencies.HILT_COMPILER)
}