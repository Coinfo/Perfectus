/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

import dependencies.Dependencies

plugins {
    id("commons.android-library")
    id(BuildPlugins.GOOGLE_SERVICES)
}

android {
    compileSdkVersion(BuildAndroidConfig.COMPILE_SDK_VERSION)
    buildToolsVersion(BuildAndroidConfig.BUILD_TOOLS_VERSION)

    defaultConfig {
        minSdkVersion(BuildAndroidConfig.MIN_SDK_VERSION)
        targetSdkVersion(BuildAndroidConfig.TARGET_SDK_VERSION)
    }

    resourcePrefix = "authenticator_"
}

dependencies {
    implementation(Dependencies.KOTLIN)
    implementation(Dependencies.CORE_KTX)
    implementation(Dependencies.APPCOMPAT)
    implementation(Dependencies.ACTIVITY_KTX)
    implementation(Dependencies.PLAY_SERVICES_AUTH)
    implementation(Dependencies.KOTLIN_X_COROUTINES_PLAY_SERVICES)
    implementation(Dependencies.FIREBASE_ANALYTICS)
    implementation(Dependencies.CONSTRAINT_LAYOUT)

    api(Dependencies.FIREBASE_AUTH_KTX)
}