/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.goals.ui.bottomsheet.menu

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import app.chamich.feature.goals.R
import app.chamich.feature.goals.databinding.GoalsBottomSheetDialogGoalActionsBinding
import app.chamich.feature.goals.model.Goal
import app.chamich.feature.goals.model.GoalStatus
import app.chamich.feature.goals.model.api.IGoal
import app.chamich.feature.goals.ui.bottomsheet.GoalsBottomSheet
import app.chamich.feature.goals.utils.EXTRA_GOAL
import app.chamich.feature.goals.utils.REQUEST_KEY_GOAL_ACTIONS
import app.chamich.library.core.model.Status
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
internal class GoalActionsMenuBottomSheet :
    GoalsBottomSheet<GoalActionsViewModel, GoalsBottomSheetDialogGoalActionsBinding>() {

    private var action = Action.NONE

    private val goal: IGoal by lazy {
        arguments?.getParcelable<Goal>(EXTRA_GOAL)
            ?: throw IllegalStateException("An error occurred while trying get Goal from extras")
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Fragment Override Functions

    override fun getLayoutId() = R.layout.goals_bottom_sheet_dialog_goal_actions

    override fun onResume() {
        super.onResume()

        // Handle back button click
        dialog?.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK
                && event.action == KeyEvent.ACTION_DOWN
                && binding.constraintLayoutActionConfirmation.isVisible
            ) {
                showActions()
                true
            } else false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBindings()
        setupObservers()
    }

    override fun getViewModelClass() = GoalActionsViewModel::class.java

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Binding Functions

    fun onDeleteClicked() {
        action = Action.DELETE
        showConfirmation(
            R.string.goals_action_menu_button_text_delete,
            R.string.goals_action_menu_message_text_delete
        )
    }

    fun onUnarchiveClicked() {
        action = Action.UNARCHIVE
        showConfirmation(
            R.string.goals_action_menu_button_text_unarchive,
            R.string.goals_action_menu_message_text_unarchive
        )
    }

    fun onArchiveClicked() {
        action = Action.ARCHIVE
        showConfirmation(
            R.string.goals_action_menu_button_text_archive,
            R.string.goals_action_menu_message_text_archive
        )
    }

    fun onCompleteClicked() {
        action = Action.COMPLETE
        showConfirmation(
            R.string.goals_action_menu_button_text_complete,
            R.string.goals_action_menu_message_text_complete
        )
    }

    fun onYesClicked() {
        when (action) {
            Action.DELETE -> viewModel.deleteGoal(goal.id)
            Action.ARCHIVE -> viewModel.changeGoalStatus(goal, GoalStatus.ARCHIVED)
            Action.UNARCHIVE -> viewModel.changeGoalStatus(goal, GoalStatus.ACTIVE)
            Action.COMPLETE -> viewModel.changeGoalStatus(goal, GoalStatus.COMPLETED)
            else -> throw java.lang.IllegalStateException("Unknown action type selected")
        }
    }

    fun onNoClicked() {
        showActions()
    }

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Private Functions

    private fun setupBindings() {
        binding.fragment = this
        binding.isArchive = goal.status != GoalStatus.ACTIVE.id
        binding.canComplete = goal.status == GoalStatus.ACTIVE.id
                && goal.totalEffort != 0
                && goal.progress * PERCENT_100 / goal.totalEffort > PERCENT_100
    }

    private fun setupObservers() {
        viewModel.actionCompleted.observe(viewLifecycleOwner, { result ->
            when (result.status) {
                Status.SUCCESS -> handleSuccess()
                Status.LOADING -> handleProgress()
                Status.FAILURE -> handleFailure()
            }
        })
    }

    private fun showConfirmation(title: Int, message: Int) {
        binding.linearLayoutActions.isVisible = false
        binding.constraintLayoutActionConfirmation.isVisible = true
        binding.textViewActionTitle.setText(title)
        binding.textViewActionMessage.text = getString(message, goal.title)
    }

    private fun showActions() {
        action = Action.NONE
        binding.linearLayoutActions.isVisible = true
        binding.constraintLayoutActionConfirmation.isVisible = false
        binding.textViewActionTitle.setText(R.string.goals_action_menu_label_actions)
    }

    private fun handleSuccess() {
        setFragmentResult(REQUEST_KEY_GOAL_ACTIONS, bundleOf())
        findNavController().navigateUp()
    }

    private fun handleFailure() {
        //
    }

    private fun handleProgress() {
        //
    }

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private enum class Action { NONE, DELETE, COMPLETE, ARCHIVE, UNARCHIVE }

    companion object {
        private const val PERCENT_100 = 100
    }
}
