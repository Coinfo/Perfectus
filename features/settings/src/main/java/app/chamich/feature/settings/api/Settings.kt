/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.settings.api

import app.chamich.library.logger.ILogger
import javax.inject.Singleton


/**
 * This class provides profile related functionality
 */
@Singleton
class Settings(private val logger: ILogger) {

    init {
        logger.debug(TAG, "|------------------------------------------------------------|")
        logger.debug(TAG, "|                           Settings                         |")
        logger.debug(TAG, "|----> Object created: ${System.identityHashCode(this)}")
    }

    fun showSettingsScreen() {
        // Not Implemented
    }

    private companion object {
        const val TAG = "Settings"
    }
}
