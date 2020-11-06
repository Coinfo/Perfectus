/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.goals.ui.modify.edit

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import app.chamich.feature.goals.R
import app.chamich.feature.goals.databinding.GoalsDialogFragmentEditGoalBinding
import app.chamich.feature.goals.model.Category
import app.chamich.feature.goals.model.Color
import app.chamich.feature.goals.model.Goal
import app.chamich.feature.goals.model.Measurement
import app.chamich.feature.goals.model.api.IGoal
import app.chamich.feature.goals.ui.modify.ModifyGoalDialogFragment
import app.chamich.feature.goals.utils.EXTRA_EDITED_GOAL
import app.chamich.feature.goals.utils.EXTRA_GOAL_ID
import app.chamich.feature.goals.utils.REQUEST_KEY_EDIT_GOAL
import app.chamich.library.core.model.Status
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat


@AndroidEntryPoint
internal class EditGoalDialogFragment :
    ModifyGoalDialogFragment<EditGoalViewModel, GoalsDialogFragmentEditGoalBinding>() {

    override fun getLayoutId() = R.layout.goals_dialog_fragment_edit_goal

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragment = this
        binding.viewmodel = viewModel

        setupObservers()

        viewModel.loadGoal(getGoalIdFromExtras())
    }

    override fun getViewModelClass() = EditGoalViewModel::class.java

    override fun updateBindings() {
        binding.viewmodel = viewModel
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Binding Functions

    fun onEditClicked() {
        val goal = (viewModel.initialGoal as Goal).copy(
            title = viewModel.title,
            progress = viewModel.progress.toInt(),
            totalEffort = viewModel.effort.toInt(),
            measuredIn = viewModel.measurement.id,
            color = viewModel.color.id,
            category = viewModel.category.id,
            completeData = viewModel.date,
        )
        setFragmentResult(REQUEST_KEY_EDIT_GOAL, bundleOf(EXTRA_EDITED_GOAL to goal))
        findNavController().navigateUp()
    }

    fun onCloseClicked() {
        findNavController().navigateUp()
    }

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private fun getGoalIdFromExtras() = arguments?.getLong(EXTRA_GOAL_ID)
        ?: throw IllegalStateException("An error occurred while trying get Goal ID from extras")

    private fun setupObservers() {
        viewModel.loadedGoal.observe(viewLifecycleOwner, { result ->
            when (result.status) {
                Status.SUCCESS -> handleGoalLoadedSuccess(result.data)
                Status.LOADING -> handleLoading()
                Status.FAILURE -> handleFailure(result.exception)
            }
        })
    }

    private fun handleGoalLoadedSuccess(goal: IGoal?) {
        viewModel.initialGoal = goal
        goal?.let {
            viewModel.title = it.title
            viewModel.effort = it.totalEffort.toString()
            viewModel.progress = it.progress.toString()
            viewModel.category = Category.valueOf(it.category)
            viewModel.measurement = Measurement.valueOf(it.measuredIn)
            viewModel.color = Color.valueOf(it.color)
            viewModel.date = it.completeData
            viewModel.dateString = SimpleDateFormat("MMM dd yyyy").format(it.completeData)

            binding.viewmodel = viewModel
        }

    }

    private fun handleLoading() {
        /* This function does nothing */
    }

    private fun handleFailure(e: Exception?) {
        /* This function does nothing */
    }
}
