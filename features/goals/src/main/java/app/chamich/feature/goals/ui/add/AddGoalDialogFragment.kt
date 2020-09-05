/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.goals.ui.add

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import app.chamich.feature.goals.R
import app.chamich.feature.goals.databinding.GoalsDialogFragmentAddGoalBinding
import app.chamich.feature.goals.model.Goal
import app.chamich.library.core.CoreDialogFragment
import app.chamich.library.core.model.Status
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
internal class AddGoalDialogFragment :
    CoreDialogFragment<AddGoalViewModel, GoalsDialogFragmentAddGoalBinding>() {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Fragment Override Functions

    override fun getLayoutId() = R.layout.goals_dialog_fragment_add_goal

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

        setupObservers()
    }

    override fun getViewModelClass() = AddGoalViewModel::class.java

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Binding Functions

    fun onAddGoalClicked() {
        viewModel.addGoal(
            Goal(
                title = binding.editTextTitle.text.toString(),
                measuredIn = 1,
                totalEffort = 1,
                progress = 1,
                completeData = 1L,
                category = 1,
                color = 1
            )
        )
    }

    fun onCancelClicked() {
        findNavController().popBackStack()
    }

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Private Functions

    private fun setupObservers() {
        viewModel.getResult().observe(this, Observer { result ->
            when (result.status) {
                Status.SUCCESS -> {
                    findNavController().popBackStack()
                }
                Status.LOADING -> { /* Do nothing */
                }
                Status.ERROR -> { /* Do nothing */
                }
            }
        })
    }

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////
}
