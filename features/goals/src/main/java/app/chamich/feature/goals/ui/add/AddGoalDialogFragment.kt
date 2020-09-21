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
import app.chamich.feature.goals.model.Category
import app.chamich.feature.goals.model.Color
import app.chamich.feature.goals.model.Goal
import app.chamich.feature.goals.model.Measurement
import app.chamich.feature.goals.ui.bottomsheet.categories.CategoriesBottomSheet
import app.chamich.feature.goals.ui.bottomsheet.colors.ColorsBottomSheet
import app.chamich.feature.goals.ui.bottomsheet.datepicker.DatePickerBottomSheet
import app.chamich.feature.goals.ui.bottomsheet.measurements.MeasurementsBottomSheet
import app.chamich.library.core.CoreDialogFragment
import app.chamich.library.core.extensions.asLong
import app.chamich.library.core.extensions.hasText
import app.chamich.library.core.model.Status
import app.chamich.library.snackbar.PerfectusSnackbar
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat


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

    fun onAddClicked() {
        // Validate the goal input data for being valid.
        val totalEffort = binding.editTextEffort.asLong
        val progress = binding.editTextProgress.asLong

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
                color = viewModel.color.id
            )
        )
    }

    fun onMeasurementClicked() {
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

    fun onCategoryClicked() {
        setFragmentResultListener(CategoriesBottomSheet.KEY_RESULT_LISTENER) { key, bundle ->
            if (CategoriesBottomSheet.KEY_RESULT_LISTENER == key) {
                viewModel.category =
                    bundle.getSerializable(CategoriesBottomSheet.KEY_RESULT_CATEGORY) as Category
                binding.viewmodel = viewModel
                clearFragmentResult(CategoriesBottomSheet.KEY_RESULT_LISTENER)
            }
        }

        // IMPROVEME: If bottom sheet is already shown, do not show it again.
        // This can happen when user very fast clicks more then once on the label.
        findNavController().navigate(
            R.id.destination_categories, bundleOf(
                CategoriesBottomSheet.KEY_CATEGORY_ID to viewModel.category.id
            )
        )
    }

    fun onDatePickerClicked() {
        setFragmentResultListener(DatePickerBottomSheet.KEY_RESULT_LISTENER) { key, bundle ->
            if (DatePickerBottomSheet.KEY_RESULT_LISTENER == key) {
                viewModel.date = bundle.getLong(DatePickerBottomSheet.KEY_RESULT_DATE)
                viewModel.dateString = SimpleDateFormat("MMM dd yyyy").format(viewModel.date)
                binding.viewmodel = viewModel
                clearFragmentResult(DatePickerBottomSheet.KEY_RESULT_LISTENER)
            }
        }
        findNavController().navigate(
            R.id.destination_date_picker,
            bundleOf(DatePickerBottomSheet.KEY_DATE to viewModel.date)
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
                Status.ERROR -> { /* Do nothing */
                }
            }
        })
    }

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////
}
