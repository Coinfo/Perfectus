/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.profile.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.chamich.feature.profile.R
import app.chamich.feature.profile.databinding.ProfileBottomSheetProfileMenuBinding
import app.chamich.library.core.CoreBottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
internal class ProfileMenuBottomSheet :
    CoreBottomSheetDialogFragment<ProfileMenuViewModel, ProfileBottomSheetProfileMenuBinding>() {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Fragment Override Functions

    override fun getLayoutId() = R.layout.profile_bottom_sheet_profile_menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Profile_BottomSheetMenu)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.a()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeBindings()
    }

    override fun getViewModelClass() = ProfileMenuViewModel::class.java

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Binding Functions

    fun onProfileClicked() {
        // Not Implemented
    }

    fun onSettingsClicked() {
        // Not Implemented
    }

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Fragment Override Functions

    private fun initializeBindings() {
        binding.fragment = this
    }

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////
}
