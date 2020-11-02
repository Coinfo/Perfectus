/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

import dependencies.Dependencies

plugins {
    id("commons.android-library")
}

android {
    compileSdkVersion(BuildAndroidConfig.COMPILE_SDK_VERSION)
    buildToolsVersion(BuildAndroidConfig.BUILD_TOOLS_VERSION)

    defaultConfig {
        minSdkVersion(BuildAndroidConfig.MIN_SDK_VERSION)
        targetSdkVersion(BuildAndroidConfig.TARGET_SDK_VERSION)
    }

    resourcePrefix = "preferences_"
}

dependencies {
    implementation(Dependencies.KOTLIN)
}
