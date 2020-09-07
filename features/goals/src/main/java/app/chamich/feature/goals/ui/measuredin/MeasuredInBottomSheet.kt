/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.goals.ui.measuredin

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import app.chamich.feature.goals.R
import app.chamich.feature.goals.databinding.GoalsBottomSheetDialogMeasuredInBinding
import app.chamich.feature.goals.model.Measurement
import app.chamich.library.core.CoreBottomSheetDialogFragment
import com.google.android.material.chip.Chip

class MeasuredInBottomSheet
    :
    CoreBottomSheetDialogFragment<MeasuredInViewModel, GoalsBottomSheetDialogMeasuredInBinding>() {

    private lateinit var selectedMeasurement: Measurement

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Fragment Override Functions

    override fun getLayoutId() = R.layout.goals_bottom_sheet_dialog_measured_in

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragment = this

        setupChips(arguments?.getInt(KEY_SENT_MEASURED_ID) ?: Measurement.HOURS.id)
    }

    override fun getViewModelClass() = MeasuredInViewModel::class.java

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

    private fun setupChips(measurementId: Int) {

        binding.chipGroupMeasuredIn.clearCheck()

        Measurement.values().asList().sortedBy { it.position }.forEach { measurement ->
            val chip = layoutInflater.inflate(
                R.layout.goals_chip_measured_in, binding.chipGroupMeasuredIn, false
            ) as Chip
            chip.id = ViewCompat.generateViewId()
            chip.isChecked = measurementId == measurement.id
            chip.setText(measurement.stringRes)
            chip.setOnClickListener { selectedMeasurement = measurement }
            binding.chipGroupMeasuredIn.addView(chip)
        }
    }

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////

    companion object {
        const val KEY_RESULT_LISTENER = "key_measurement_in_result_listener"
        const val KEY_SENT_MEASURED_ID = "key_measurement_sent_measured_id"
        const val KEY_RESULT_MEASUREMENT = "key_measurement_result_measurement"
    }
}
