/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.goals.ui.add

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.clearFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import app.chamich.feature.goals.R
import app.chamich.feature.goals.databinding.GoalsDialogFragmentAddGoalBinding
import app.chamich.feature.goals.model.Color
import app.chamich.feature.goals.model.Goal
import app.chamich.feature.goals.model.Measurement
import app.chamich.feature.goals.ui.bottomsheet.colors.ColorsBottomSheet
import app.chamich.feature.goals.ui.bottomsheet.measurements.MeasurementsBottomSheet
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
        binding.viewmodel = viewModel

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
                measuredIn = viewModel.measurement.id,
                totalEffort = 1,
                progress = 1,
                completeData = 1L,
                category = 1,
                color = viewModel.color.id
            )
        )
    }

    fun onMeasuredInClicked() {
        // Sets the fragment result listener
        setFragmentResultListener(MeasurementsBottomSheet.KEY_RESULT_LISTENER) { key, bundle ->
            if (MeasurementsBottomSheet.KEY_RESULT_LISTENER == key) {
                viewModel.measurement =
                    bundle.getSerializable(MeasurementsBottomSheet.KEY_RESULT_MEASUREMENT) as Measurement
                binding.viewmodel = viewModel
                clearFragmentResult(MeasurementsBottomSheet.KEY_RESULT_LISTENER)
            }
        }

        // IMPROVEME: If bottom sheet is already shown, do not show it again.
        // This can happen when user very fast clicks more then once on the label.
        findNavController().navigate(
            R.id.destination_measured_in, bundleOf(
                MeasurementsBottomSheet.KEY_MEASURED_ID to viewModel.measurement.id
            )
        )
    }

    fun onColorClicked() {
        setFragmentResultListener(ColorsBottomSheet.KEY_RESULT_LISTENER) { key, bundle ->
            if (ColorsBottomSheet.KEY_RESULT_LISTENER == key) {
                viewModel.color =
                    bundle.getSerializable(ColorsBottomSheet.KEY_RESULT_COLOR) as Color
                binding.viewmodel = viewModel
                clearFragmentResult(ColorsBottomSheet.KEY_RESULT_LISTENER)
            }
        }

        // IMPROVEME: If bottom sheet is already shown, do not show it again.
        // This can happen when user very fast clicks more then once on the label.
        findNavController().navigate(
            R.id.destination_colors, bundleOf(
                ColorsBottomSheet.KEY_COLOR_ID to viewModel.color.id
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
        viewModel.getResult().observe(viewLifecycleOwner, Observer { result ->
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
