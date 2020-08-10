/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.settings.ui.settings

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import app.chamich.feature.settings.R
import app.chamich.library.logger.ILogger
import app.chamich.library.preferences.IPreferences
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.settings_fragment_general_settings.*
import javax.inject.Inject


@AndroidEntryPoint
class GeneralSettingsFragment : Fragment() {

    @Inject
    lateinit var logger: ILogger

    @Inject
    lateinit var preferences: IPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.settings_fragment_general_settings, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeToggleButton()
        addToggleButtonCheckedListener()
    }


    private fun initializeToggleButton() {
        togglebutton_theme.check(
            (
                    preferences.loadInt(KEY_PREFERENCE_THEME_MODE)
                        ?: resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
                    ).getCheckedToggleButtonId
        )
    }

    private fun addToggleButtonCheckedListener() {
        togglebutton_theme.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (isChecked) {
                val mode = when (checkedId) {
                    R.id.button_theme_light -> AppCompatDelegate.MODE_NIGHT_NO
                    R.id.button_theme_dark -> AppCompatDelegate.MODE_NIGHT_YES
                    else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                }

                preferences.saveInt(KEY_PREFERENCE_THEME_MODE, mode)
                AppCompatDelegate.setDefaultNightMode(mode)

            }
        }
    }

    private companion object {
        const val KEY_PREFERENCE_THEME_MODE = "key_preference_mode"
    }
}

private val Int.getCheckedToggleButtonId
    get() = when {
        this == AppCompatDelegate.MODE_NIGHT_NO || this == Configuration.UI_MODE_NIGHT_NO ->
            R.id.button_theme_light
        this == AppCompatDelegate.MODE_NIGHT_YES || this == Configuration.UI_MODE_NIGHT_YES ->
            R.id.button_theme_dark
        else -> R.id.button_theme_system
    }
