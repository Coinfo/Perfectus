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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        dataBinding = true
    }

    resourcePrefix = "profile_"
}

dependencies {
    implementation(project(BuildModules.Libraries.CORE))
    implementation(project(BuildModules.Libraries.LOGGER))
    implementation(project(BuildModules.Libraries.DESIGN))

    implementation(Dependencies.KOTLIN)
    implementation(Dependencies.CORE_KTX)
    implementation(Dependencies.HILT_ANDROID)
    implementation("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha02")

    kapt(AnnotationProcessorsDependencies.HILT_COMPILER)
    kapt(AnnotationProcessorsDependencies.HILT_ANDROID_COMPILER)
}