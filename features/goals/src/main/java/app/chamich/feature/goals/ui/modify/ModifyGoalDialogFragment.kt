/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.goals.ui.modify

import android.os.Bundle
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.clearFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import app.chamich.feature.goals.R
import app.chamich.feature.goals.model.Category
import app.chamich.feature.goals.model.Color
import app.chamich.feature.goals.model.Measurement
import app.chamich.feature.goals.ui.bottomsheet.categories.CategoriesBottomSheet
import app.chamich.feature.goals.ui.bottomsheet.colors.ColorsBottomSheet
import app.chamich.feature.goals.ui.bottomsheet.datepicker.DatePickerBottomSheet
import app.chamich.feature.goals.ui.bottomsheet.measurements.MeasurementsBottomSheet
import app.chamich.library.core.CoreDialogFragment
import java.text.SimpleDateFormat

internal abstract class ModifyGoalDialogFragment<VM : ModifyGoalViewModel, VDB : ViewDataBinding> :
    CoreDialogFragment<VM, VDB>() {

    abstract fun updateBindings()

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


    fun onMeasurementClicked() {
        // Sets the fragment result listener
        setFragmentResultListener(MeasurementsBottomSheet.KEY_RESULT_LISTENER) { key, bundle ->
            if (MeasurementsBottomSheet.KEY_RESULT_LISTENER == key) {
                viewModel.measurement =
                    bundle.getSerializable(MeasurementsBottomSheet.KEY_RESULT_MEASUREMENT) as Measurement
                updateBindings()
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
                updateBindings()
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
                updateBindings()
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
                updateBindings()
                clearFragmentResult(DatePickerBottomSheet.KEY_RESULT_LISTENER)
            }
        }
        findNavController().navigate(
            R.id.destination_date_picker,
            bundleOf(DatePickerBottomSheet.KEY_DATE to viewModel.date)
        )
    }


}
