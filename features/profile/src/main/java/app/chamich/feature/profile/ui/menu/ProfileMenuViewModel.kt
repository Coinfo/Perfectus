/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.profile.ui.menu

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import app.chamich.library.authentication.IAuthenticator
import app.chamich.library.authentication.IUser
import app.chamich.library.logger.ILogger
import app.chamich.library.preferences.IPreferences
import java.lang.IllegalStateException

internal class ProfileMenuViewModel @ViewModelInject constructor(
    private val logger: ILogger,
    private val authenticator: IAuthenticator,
    private val preferences: IPreferences
) : ViewModel() {

    fun getCurrentUser(): IUser {
        return authenticator.getCurrentUser()
            ?: throw IllegalStateException("Unable to get current user")
    }

    fun getThemeMode(): Int? = preferences.loadInt(KEY_PREFERENCE_THEME_MODE)

    fun setThemeMode(mode: Int) {
        preferences.saveInt(KEY_PREFERENCE_THEME_MODE, mode)
    }

    companion object {
        private const val KEY_PREFERENCE_THEME_MODE = "key_preference_mode"
    }
}
