package app.chamich

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import app.chamich.library.preferences.IPreferences
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class PerfectusActivity : AppCompatActivity() {

    @Inject
    lateinit var preferences: IPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfectus)

        // Set application theme mode
        preferences.loadInt(KEY_PREFERENCE_THEME_MODE)?.let { mode ->
            AppCompatDelegate.setDefaultNightMode(mode)
        }
    }

    private companion object {
        const val KEY_PREFERENCE_THEME_MODE = "key_preference_mode"
    }
}
