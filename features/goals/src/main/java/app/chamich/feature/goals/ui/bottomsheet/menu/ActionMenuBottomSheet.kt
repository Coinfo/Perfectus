/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.goals.ui.bottomsheet.menu

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
//import androidx.navigation.fragment.findNavController
import app.chamich.feature.goals.R
import app.chamich.feature.goals.databinding.GoalsBottomSheetDialogActionMenuBinding
import app.chamich.feature.goals.ui.bottomsheet.GoalsBottomSheet

internal class ActionMenuBottomSheet :
    GoalsBottomSheet<GoalsBottomSheetDialogActionMenuBinding>() {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Fragment Override Functions

    override fun getLayoutId() = R.layout.goals_bottom_sheet_dialog_action_menu

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBindings()
    }

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Binding Functions

    fun onEditClicked() {
        setFragmentResultAndNavigateUp(Action.EDIT)
    }

    fun onDeleteClicked() {
        setFragmentResultAndNavigateUp(Action.DELETE)
    }

    fun onArchiveClicked() {
        setFragmentResultAndNavigateUp(Action.ARCHIVE)
    }

    fun onCompleteClicked() {
        setFragmentResultAndNavigateUp(Action.COMPLETE)
    }

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Private Functions

    private fun setupBindings() {
        binding.fragment = this
    }

    private fun setFragmentResultAndNavigateUp(action: Action) {
        setFragmentResult(REQUEST_KEY_ACTION, bundleOf(KET_ACTION to action))
    }

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////

    enum class Action {
        EDIT, DELETE, ARCHIVE, COMPLETE
    }

    companion object {
        const val REQUEST_KEY_ACTION = "REQUEST_KEY_ACTION"
        const val KET_ACTION = "KET_ACTION"
    }
}
