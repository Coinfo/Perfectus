/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.goals.ui.modify.edit

import app.chamich.feature.goals.R
import app.chamich.feature.goals.databinding.GoalsDialogFragmentEditGoalBinding
import app.chamich.feature.goals.ui.modify.ModifyGoalDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class EditGoalDialogFragment :
    ModifyGoalDialogFragment<EditGoalViewModel, GoalsDialogFragmentEditGoalBinding>() {

    override fun updateBindings() {
        TODO("Not yet implemented")
    }

    override fun getViewModelClass() = EditGoalViewModel::class.java

    override fun getLayoutId() = R.layout.goals_dialog_fragment_edit_goal
}
