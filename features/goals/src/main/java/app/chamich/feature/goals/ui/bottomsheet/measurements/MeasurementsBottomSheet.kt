/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.goals.ui.bottomsheet.measurements

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import app.chamich.feature.goals.R
import app.chamich.feature.goals.databinding.GoalsBottomSheetDialogMeasurementsBinding
import app.chamich.feature.goals.model.Measurement
import app.chamich.feature.goals.ui.bottomsheet.GoalsBottomSheet

internal class MeasurementsBottomSheet :
    GoalsBottomSheet<ViewModel, GoalsBottomSheetDialogMeasurementsBinding>() {

    private lateinit var selectedMeasurement: Measurement

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Fragment Override Functions

    override fun getLayoutId() = R.layout.goals_bottom_sheet_dialog_measurements

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBindings()
        setupChips(getMeasurementId())
    }

    override fun getViewModelClass() = ViewModel::class.java

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Binding Functions

    fun onUseMeasurementClicked() {
        setFragmentResult(
            KEY_RESULT_LISTENER,
            bundleOf(KEY_RESULT_MEASUREMENT to selectedMeasurement)
        )
        findNavController().navigateUp()
    }

    fun onCancelClicked() {
        findNavController().navigateUp()
    }

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Private Functions

    private fun setupBindings() {
        binding.fragment = this
    }

    private fun setupChips(measurementId: Int) =
        Measurement.asList().forEach { measurement ->
            binding.chipGroupMeasuredIn.addView(
                inflateAsChip(R.layout.goals_chip_measurement, binding.chipGroupMeasuredIn).apply {
                    id = ViewCompat.generateViewId()
                    isChecked = measurementId == measurement.id
                    setText(measurement.stringRes)
                    setOnClickListener { selectedMeasurement = measurement }
                })
        }

    private fun getMeasurementId() =
        arguments?.getInt(KEY_MEASURED_ID) ?: Measurement.defaultMeasurement().id

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////

    companion object {
        const val KEY_MEASURED_ID = "KEY_MEASURED_ID"
        const val KEY_RESULT_MEASUREMENT = "KEY_RESULT_MEASUREMENT"
        const val KEY_RESULT_LISTENER = "KEY_RESULT_LISTENER"
    }
}
