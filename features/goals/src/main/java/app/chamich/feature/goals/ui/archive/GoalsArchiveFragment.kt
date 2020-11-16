/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.goals.ui.archive

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import app.chamich.feature.goals.R
import app.chamich.feature.goals.databinding.GoalsDialogFragmentArchiveBinding
import app.chamich.feature.goals.model.GoalStatus
import app.chamich.feature.goals.model.api.IGoal
import app.chamich.library.core.CoreDialogFragment
import app.chamich.library.core.model.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class GoalsArchiveFragment :
    CoreDialogFragment<GoalsArchiveViewModel, GoalsDialogFragmentArchiveBinding>() {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Fragment Override Functions

    override fun getLayoutId() = R.layout.goals_dialog_fragment_archive

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

        initializeToggleButton()
        setupObservers()
    }

    override fun getViewModelClass() = GoalsArchiveViewModel::class.java

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Fragment Override Functions

    private fun initializeToggleButton() {
        binding.togglebuttonTheme.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.button_archived -> loadArchivedGoals(GoalStatus.ARCHIVED)
                    R.id.button_completed -> loadArchivedGoals(GoalStatus.COMPLETED)
                    else -> throw IllegalStateException("Unknown button id")
                }
            }
        }
        binding.togglebuttonTheme.check(
            if (viewModel.status == GoalStatus.ARCHIVED) R.id.button_archived
            else R.id.button_completed
        )
    }

    private fun setupObservers() {
        viewModel.archivedGoals.observe(viewLifecycleOwner, { result ->
            when (result.status) {
                Status.SUCCESS -> handleSuccess(result.data)
                Status.LOADING -> handleProgress()
                Status.FAILURE -> handleFailure(result.data)
            }
        })
    }

    private fun loadArchivedGoals(status: GoalStatus) {
        viewModel.status = status
        viewModel.loadArchivedGoals()
    }

    private fun handleSuccess(data: List<IGoal>?) {
        //
    }

    private fun handleFailure(data: List<IGoal>?) {
        // Do nothing
    }

    private fun handleProgress() {
        // Do nothing
    }

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////

}
