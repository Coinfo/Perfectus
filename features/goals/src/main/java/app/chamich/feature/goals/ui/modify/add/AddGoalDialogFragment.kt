/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.goals.ui.modify.add

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import app.chamich.feature.goals.R
import app.chamich.feature.goals.databinding.GoalsDialogFragmentAddGoalBinding
import app.chamich.feature.goals.model.Goal
import app.chamich.feature.goals.model.GoalStatus
import app.chamich.feature.goals.ui.modify.ModifyGoalDialogFragment
import app.chamich.library.core.extensions.asInt
import app.chamich.library.core.extensions.hasText
import app.chamich.library.core.model.Status
import app.chamich.library.snackbar.PerfectusSnackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
internal class AddGoalDialogFragment :
    ModifyGoalDialogFragment<AddGoalViewModel, GoalsDialogFragmentAddGoalBinding>() {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Fragment Override Functions

    override fun getLayoutId() = R.layout.goals_dialog_fragment_add_goal

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragment = this
        binding.viewmodel = viewModel

        setupObservers()
    }

    override fun getViewModelClass() = AddGoalViewModel::class.java

    override fun updateBindings() {
        binding.viewmodel = viewModel
    }

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Binding Functions

    fun onAddClicked() {
        // Validate the goal input data for being valid.
        val totalEffort = binding.editTextEffort.asInt
        val progress = binding.editTextProgress.asInt

        if (!binding.editTextGoalTitle.hasText) {
            view?.let {
                PerfectusSnackbar.make(it, R.string.goals_error_text_missing_title).show()
            }
            return
        }

        if (progress > totalEffort) {
            view?.let {
                PerfectusSnackbar.make(it, R.string.goals_error_text_progress_more_then_effort)
                    .show()
            }
            return
        }

        viewModel.addGoal(
            Goal(
                title = binding.editTextGoalTitle.text.toString(),
                measuredIn = viewModel.measurement.id,
                totalEffort = totalEffort,
                progress = progress,
                completeData = viewModel.date,
                category = viewModel.category.id,
                color = viewModel.color.id,
                creationDate = System.currentTimeMillis(),
                status = GoalStatus.IN_PROGRESS.id
            )
        )
    }


    fun onCloseClicked() {
        findNavController().popBackStack()
    }

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Private Functions

    private fun setupObservers() {
        viewModel.getResult().observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Status.SUCCESS -> {
                    findNavController().popBackStack()
                }
                Status.LOADING -> { /* Do nothing */
                }
                Status.FAILURE -> { /* Do nothing */
                }
            }
        })
    }

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////
}
