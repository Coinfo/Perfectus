/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.profile.ui.menu

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import app.chamich.feature.profile.R
import app.chamich.feature.profile.databinding.ProfileBottomSheetProfileMenuBinding
import app.chamich.library.core.CoreBottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
internal class ProfileMenuBottomSheet :
    CoreBottomSheetDialogFragment<ProfileMenuViewModel, ProfileBottomSheetProfileMenuBinding>() {

    private var listener: SignOutListener? = null

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Fragment Override Functions

    override fun getLayoutId() = R.layout.profile_bottom_sheet_profile_menu

    override fun onAttach(context: Context) {
        super.onAttach(context)

        listener = context as? SignOutListener ?: throw IllegalStateException(
            "In order to listen for Profile action events, your" +
                    " activity should implement ProfileMenuActionListener"
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Profile_BottomSheetMenu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeBindings()
        initializeToggleButton()
        addToggleButtonCheckedListener()
        renderUserProfileData()
    }

    override fun getViewModelClass() = ProfileMenuViewModel::class.java

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Binding Functions

    fun onSignOutClicked() {
        listener?.onSignOut()
    }

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Fragment Override Functions

    private fun initializeBindings() {
        binding.fragment = this
    }

    private fun renderUserProfileData() {
        val user = viewModel.getCurrentUser()
        binding.textViewUsername.text = user.displayName
        binding.textViewEmail.text = user.email
    }

    private fun initializeToggleButton() {
        binding.togglebuttonTheme.check(
            (viewModel.getThemeMode()
                ?: resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK).getButtonId
        )
    }

    private fun addToggleButtonCheckedListener() {
        binding.togglebuttonTheme.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (isChecked) {
                val mode = when (checkedId) {
                    R.id.button_theme_light -> AppCompatDelegate.MODE_NIGHT_NO
                    R.id.button_theme_dark -> AppCompatDelegate.MODE_NIGHT_YES
                    else -> AppCompatDelegate.MODE_NIGHT_YES
                }

                viewModel.setThemeMode(mode)
                AppCompatDelegate.setDefaultNightMode(mode)
            }
        }
    }

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private val Int.getButtonId
        get() = when {
            this == AppCompatDelegate.MODE_NIGHT_NO || this == Configuration.UI_MODE_NIGHT_NO ->
                R.id.button_theme_light
            this == AppCompatDelegate.MODE_NIGHT_YES || this == Configuration.UI_MODE_NIGHT_YES ->
                R.id.button_theme_dark
            else -> R.id.button_theme_dark
        }

}
