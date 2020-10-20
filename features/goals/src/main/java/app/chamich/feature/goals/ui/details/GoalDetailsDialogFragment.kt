/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.goals.ui.details

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import app.chamich.feature.goals.R
import app.chamich.feature.goals.databinding.GoalsDialogFragmentGoalDetailsBinding
import app.chamich.feature.goals.model.api.IGoal
import app.chamich.feature.goals.utils.EXTRA_GOAL_ID
import app.chamich.feature.goals.utils.REQUEST_KEY_GOAL_DETAILS
import app.chamich.library.core.CoreDialogFragment
import app.chamich.library.core.model.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class GoalDetailsDialogFragment :
    CoreDialogFragment<GoalDetailsViewModel, GoalsDialogFragmentGoalDetailsBinding>() {

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

    fun onAddProgressClicked() {
        viewModel.updateGoal()
    }

    fun onCloseClicked() {
        findNavController().navigateUp()
    }

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Private Functions

    private fun setupObservers() {
        viewModel.getLoadGoalResult().observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Status.SUCCESS -> handleLoadGoalSuccess(result.data)
                Status.LOADING -> handleLoading()
                Status.FAILURE -> handleFailure(result.exception)
            }
        })

        viewModel.getUpdateGoalResult().observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Status.SUCCESS -> handleUpdateGoalSuccess()
                Status.LOADING -> handleLoading()
                Status.FAILURE -> handleFailure(result.exception)
            }
        })

        viewModel.getProgress().observe(viewLifecycleOwner, Observer { result ->
            binding.viewmodel = viewModel
        })
    }

    private fun handleLoadGoalSuccess(goal: IGoal?) {
        goal?.let {
            binding.goal = it
            binding.executePendingBindings()
        }
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
