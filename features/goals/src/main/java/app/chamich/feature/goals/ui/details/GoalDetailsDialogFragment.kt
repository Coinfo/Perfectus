/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.goals.ui.details

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.clearFragmentResult
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import app.chamich.feature.goals.R
import app.chamich.feature.goals.databinding.GoalsDialogFragmentGoalDetailsBinding
import app.chamich.feature.goals.model.Goal
import app.chamich.feature.goals.model.api.IGoal
import app.chamich.feature.goals.ui.bottomsheet.menu.ActionMenuBottomSheet
import app.chamich.feature.goals.ui.bottomsheet.menu.ActionMenuBottomSheet.Companion.KET_ACTION
import app.chamich.feature.goals.ui.bottomsheet.menu.ActionMenuBottomSheet.Companion.REQUEST_KEY_ACTION
import app.chamich.feature.goals.utils.EXTRA_EDITED_GOAL
import app.chamich.feature.goals.utils.EXTRA_GOAL_ID
import app.chamich.feature.goals.utils.REQUEST_KEY_EDIT_GOAL
import app.chamich.feature.goals.utils.REQUEST_KEY_GOAL_DETAILS
import app.chamich.library.core.CoreDialogFragment
import app.chamich.library.core.model.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class GoalDetailsDialogFragment :
    CoreDialogFragment<GoalDetailsViewModel, GoalsDialogFragmentGoalDetailsBinding>() {

    private lateinit var currentGoal: IGoal

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Fragment Override Functions

    override fun getLayoutId() = R.layout.goals_dialog_fragment_goal_details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NORMAL, R.style.Perfectus_FullScreenDialog)
    }

    override fun onStart() {
        super.onStart()

        dialog?.let { dialog ->
            dialog.window?.let { window ->
                window.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                window.setWindowAnimations(R.style.Slide)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragment = this
        binding.viewmodel = viewModel

        setupObservers()

        viewModel.loadGoal(getGoalIdFromExtras())
    }

    override fun getViewModelClass() = GoalDetailsViewModel::class.java

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Binding Functions

    fun onSaveClicked() {
        val goal = (currentGoal as Goal).copy(
            progress = currentGoal.progress + viewModel.getProgress().value!!
        )
        viewModel.updateGoal(goal)
    }

    fun onCloseClicked() {
        findNavController().navigateUp()
    }

    fun onMenuClicked() {
        setFragmentResultListener(REQUEST_KEY_ACTION) { key, bundle ->
            if (REQUEST_KEY_ACTION == key) {
                val action = bundle.getSerializable(KET_ACTION) as ActionMenuBottomSheet.Action
                when (action) {
                    ActionMenuBottomSheet.Action.EDIT -> onEditGoalClicked()
                    ActionMenuBottomSheet.Action.DELETE -> onDeleteGoalClicked()
                    ActionMenuBottomSheet.Action.ARCHIVE -> onArchiveClicked()
                    ActionMenuBottomSheet.Action.COMPLETE -> onCompleteClicked()
                }
                clearFragmentResult(REQUEST_KEY_ACTION)
            }
        }

        findNavController().navigate(R.id.destination_action_menu)
    }

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Private Functions

    private fun onDeleteGoalClicked() {
        viewModel.deleteGoal(currentGoal.id)

        findNavController().navigateUp()
    }

    private fun onEditGoalClicked() {
        setFragmentResultListener(REQUEST_KEY_EDIT_GOAL) { key, bundle ->
            if (REQUEST_KEY_EDIT_GOAL == key) {
                val goal = bundle.getParcelable<Goal>(EXTRA_EDITED_GOAL) as IGoal
                currentGoal = goal
                binding.goal = goal
                binding.executePendingBindings()

                findNavController().navigateUp()

                clearFragmentResult(REQUEST_KEY_EDIT_GOAL)
            }
        }

        findNavController().navigate(
            R.id.destination_edit_goal,
            bundleOf(REQUEST_KEY_EDIT_GOAL to currentGoal)
        )
    }

    private fun onArchiveClicked() {
        findNavController().navigateUp()
    }

    private fun onCompleteClicked() {
        findNavController().navigateUp()
    }

    private fun setupObservers() {
        viewModel.deleteGoal.observe(viewLifecycleOwner, { result ->
            when (result.status) {
                Status.SUCCESS -> handleDeleteGoalSuccess()
                Status.LOADING -> handleLoading()
                Status.FAILURE -> handleFailure(result.exception)
            }
        })


        viewModel.loadGoal.observe(viewLifecycleOwner, { result ->
            when (result.status) {
                Status.SUCCESS -> handleLoadGoalSuccess(result.data)
                Status.LOADING -> handleLoading()
                Status.FAILURE -> handleFailure(result.exception)
            }
        })

        viewModel.updateGoal.observe(viewLifecycleOwner, { result ->
            when (result.status) {
                Status.SUCCESS -> handleUpdateGoalSuccess()
                Status.LOADING -> handleLoading()
                Status.FAILURE -> handleFailure(result.exception)
            }
        })

        viewModel.getProgress().observe(viewLifecycleOwner, { result ->
            binding.viewmodel = viewModel
        })
    }

    private fun handleLoadGoalSuccess(goal: IGoal?) {
        goal?.let {
            currentGoal = it
            binding.goal = it
            binding.executePendingBindings()
        }
    }

    private fun handleDeleteGoalSuccess() {
        setFragmentResult(REQUEST_KEY_GOAL_DETAILS, bundleOf())
        findNavController().navigateUp()
    }

    private fun handleUpdateGoalSuccess() {
        setFragmentResult(REQUEST_KEY_GOAL_DETAILS, bundleOf())
        findNavController().navigateUp()
    }

    private fun handleLoading() {
        /* This function does nothing */
    }

    private fun handleFailure(e: Exception?) {
        /* This function does nothing */
    }

    private fun getGoalIdFromExtras() = arguments?.getLong(EXTRA_GOAL_ID)
        ?: throw IllegalStateException("An error occurred while trying get Goal ID from extras")

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////
}
