/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.profile.api

import androidx.fragment.app.FragmentManager
import app.chamich.feature.profile.ui.menu.ProfileMenuBottomSheet
import app.chamich.library.logger.ILogger
import javax.inject.Singleton

/**
 * This class provides profile related functionality
 */
@Singleton
class Profile(private val logger: ILogger) {

    init {
        logger.debug(TAG, "|------------------------------------------------------------|")
        logger.debug(TAG, "|                           Profile                          |")
        logger.debug(TAG, "|----> Object created: ${System.identityHashCode(this)}")
    }

    /**
     * Shows profile bottom sheet menu
     */
    fun showProfileScreen(fm: FragmentManager?) {
        fm?.let {
            (it.findFragmentByTag(TAG_PROFILE_MENU_BOTTOM_SHEET) as? ProfileMenuBottomSheet
                ?: ProfileMenuBottomSheet()).show(
                it,
                TAG_PROFILE_MENU_BOTTOM_SHEET
            )
        }
    }

    private companion object {
        const val TAG = "Profile"
        const val TAG_PROFILE_MENU_BOTTOM_SHEET =
            "app.chamich.feature.profile.TAG_PROFILE_MENU_BOTTOM_SHEET"
    }
}
