/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.perfectus

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import app.chamich.feature.authentication.ui.signin.SignInListener
import app.chamich.feature.authentication.ui.signup.SignUpListener
import app.chamich.feature.profile.ui.menu.SignOutListener
import app.chamich.library.authentication.IAuthenticator
import app.chamich.library.authentication.IUser
import app.chamich.library.preferences.IPreferences
import app.chamich.perfectus.databinding.ActivityPerfectusBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class PerfectusActivity : AppCompatActivity(),
    SignInListener, SignUpListener, SignOutListener {

    private lateinit var binding: ActivityPerfectusBinding

    @Inject
    lateinit var authenticator: IAuthenticator

    private val navController: NavController by lazy { findNavController(R.id.nav_host_fragment) }

    @Inject
    lateinit var preferences: IPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_perfectus)

        // Set application theme mode
        preferences.loadInt(KEY_PREFERENCE_THEME_MODE)?.let { mode ->
            AppCompatDelegate.setDefaultNightMode(mode)
        }

        // When the activity is created first time and user is signed in
        // navigate to the Goals screen, instead of authentication (default)
        if (savedInstanceState == null && authenticator.isSignedIn()) {
            navigateToPerfectus()
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Private Function


    private fun navigateToPerfectus() {
        navController.popBackStack()
        navController.navigate(R.id.destination_fragment_perfectus)
    }


    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region SignInListener

    override fun onSignInCompleted(user: IUser) {
        navigateToPerfectus()
    }

    //endregion

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region SignUpListener

    override fun onSignUpCompleted(user: IUser) {
        navigateToPerfectus()
    }

    //endregion

    override fun onSignOut() {
        authenticator.signOut()
        navController.popBackStack()
        navController.navigate(R.id.navigation_authentication)
    }

    private companion object {
        const val KEY_PREFERENCE_THEME_MODE = "key_preference_mode"
    }
}
