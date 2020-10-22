/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.profile.ui

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.a()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun getViewModelClass() = ProfileMenuViewModel::class.java

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////

}
