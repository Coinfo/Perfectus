/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.goals.ui.details

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import app.chamich.feature.goals.R
import app.chamich.feature.goals.databinding.GoalsDialogFragmentGoalDetailsBinding
import app.chamich.feature.goals.model.api.IGoal
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
    //region Private Functions

    private fun setupObservers() {
        viewModel.getResult().observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Status.SUCCESS -> renderGoal(result.data)
                Status.LOADING -> { /* Do nothing */
                }
                Status.FAILURE -> { /* Do nothing */
                }
            }
        })
    }

    private fun renderGoal(goal: IGoal?) {
        goal?.let {
            binding.goal = it
            binding.executePendingBindings()
        }
    }

    private fun getGoalIdFromExtras() = arguments?.getLong(EXTRA_GOAL_ID)
        ?: throw IllegalStateException("Unable to get Goal ID")

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////

    companion object {
        const val EXTRA_GOAL_ID = "app.chamich.feature.goals.EXTRA_GOAL_ID"
    }
}
