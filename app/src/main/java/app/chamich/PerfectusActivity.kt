package app.chamich

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import app.chamich.databinding.ActivityPerfectusBinding
import app.chamich.feature.authentication.ui.signin.SignInListener
import app.chamich.feature.authentication.ui.signup.SignUpListener
import app.chamich.library.authentication.IAuthenticator
import app.chamich.library.authentication.IUser
import app.chamich.library.preferences.IPreferences
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class PerfectusActivity : AppCompatActivity(),
    SignInListener, SignUpListener {

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

        binding.appbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem?.itemId) {
                R.id.action_sign_out -> {
                    authenticator.signOut()
//                    navController.setGraph(R.navigation.settings_navigation)
                }
                R.id.action_settings -> {
                    navController.navigate(R.id.navigation_settings)
                    //navController.setGraph(R.navigation.settings_navigation)
                }
            }
            return@setOnMenuItemClickListener true
        }

        if (authenticator.isSignedIn()) {
            binding.appbar.isVisible = true
            binding.fabSearch.isVisible = true
            // Call navController.setGraph(...) with providing correct graph.
        }

        addDestinationChangeListener()
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Private Function

    private fun addDestinationChangeListener() {
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.destination_fragment_sign_in,
                R.id.destination_fragment_sign_up,
                R.id.destination_forgot_password -> {
                    // Hides the Title and the Bottom Application Bar
                    supportActionBar?.hide()
                    binding.appbar.isVisible = false
                    binding.fabSearch.isVisible = false
                }
            }

        }
    }

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region SignInListener

    override fun onSignInCompleted(user: IUser) {
        // Call navController.setGraph(...) with providing correct graph.
        binding.appbar.isVisible = true
        binding.fabSearch.isVisible = true
    }

    //endregion

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region SignUpListener

    override fun onSignUpCompleted(user: IUser) {
        // Call navController.setGraph(...) with providing correct graph.
        binding.appbar.isVisible = true
        binding.fabSearch.isVisible = true
    }

    //endregion

    private companion object {
        const val KEY_PREFERENCE_THEME_MODE = "key_preference_mode"
    }
}
