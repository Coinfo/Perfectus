/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.goals.ui.bottomsheet.datepicker

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import app.chamich.feature.goals.R
import app.chamich.feature.goals.databinding.GoalsBottomSheetDialogDatePickerBinding
import app.chamich.feature.goals.ui.bottomsheet.GoalsBottomSheet
import java.util.*

internal class DatePickerBottomSheet :
    GoalsBottomSheet<GoalsBottomSheetDialogDatePickerBinding>() {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Fragment Override Functions

    override fun getLayoutId() = R.layout.goals_bottom_sheet_dialog_date_picker

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBindings()
        setupNumberPickers(getDate())
    }

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Binding Functions

    fun onUseClicked() {
        setFragmentResult(
            KEY_RESULT_LISTENER,
            bundleOf(
                KEY_RESULT_DATE to GregorianCalendar(
                    binding.numberPickerYears.value,
                    binding.numberPickerMonths.value,
                    binding.numberPickerDays.value
                ).timeInMillis
            )
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

    private fun setupNumberPickers(date: Long) {

        val calendar = Calendar.getInstance()
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentYear = calendar.get(Calendar.YEAR)
        val currentDaysOfMonth = getDaysInMonth(currentYear, currentMonth)

        // Setup the Days
        binding.numberPickerDays.apply {
            wrapSelectorWheel = false
            minValue = currentDay
            maxValue = currentDaysOfMonth
            value = currentDay
        }

        setupMonthsPicker(currentDay, currentMonth, currentYear)
        setupYearsPicker(currentDay, currentMonth, currentYear)

        if (date != 0L) {
            val currentDateCalendar = Calendar.getInstance()
            currentDateCalendar.timeInMillis = date
            binding.numberPickerDays.value = currentDateCalendar.get(Calendar.DAY_OF_MONTH)
            binding.numberPickerMonths.value = currentDateCalendar.get(Calendar.MONTH)
            binding.numberPickerYears.value = currentDateCalendar.get(Calendar.YEAR)
        }
    }

    private fun setupMonthsPicker(currentDay: Int, currentMonth: Int, currentYear: Int) {
        binding.numberPickerMonths.apply {
            wrapSelectorWheel = false
            minValue = currentMonth
            maxValue = MAXIMUM_MONTHS
            value = currentMonth
            setOnValueChangedListener { _, _, selectedMonth ->
                if (selectedMonth == currentMonth && binding.numberPickerYears.value == currentYear) {
                    binding.numberPickerDays.minValue = currentDay
                    binding.numberPickerDays.maxValue = getDaysInMonth(currentYear, currentMonth)
                } else {
                    binding.numberPickerDays.minValue = STARTING_DAY
                    binding.numberPickerDays.maxValue = getDaysInMonth(
                        binding.numberPickerYears.value, selectedMonth
                    )
                }
            }
        }
    }

    private fun setupYearsPicker(currentDay: Int, currentMonth: Int, currentYear: Int) {
        binding.numberPickerYears.apply {
            wrapSelectorWheel = false
            minValue = currentYear
            maxValue = currentYear + MAXIMUM_YEARS
            value = currentYear
            setOnValueChangedListener { _, _, selectedYear ->
                if (selectedYear == currentYear) {
                    binding.numberPickerMonths.minValue = currentMonth
                    binding.numberPickerMonths.maxValue = MAXIMUM_MONTHS
                    binding.numberPickerDays.minValue = currentDay
                    binding.numberPickerDays.maxValue = getDaysInMonth(currentYear, currentMonth)
                } else {
                    binding.numberPickerMonths.minValue = STARTING_MONTH
                    binding.numberPickerMonths.maxValue = MAXIMUM_MONTHS
                    binding.numberPickerDays.minValue = STARTING_DAY
                    binding.numberPickerDays.maxValue = getDaysInMonth(
                        selectedYear, binding.numberPickerMonths.value
                    )
                }
            }
        }
    }

    private fun getDaysInMonth(year: Int, month: Int) =
        GregorianCalendar(year, month, STARTING_DAY).getActualMaximum(Calendar.DAY_OF_MONTH)

    private fun getDate() =
        arguments?.getLong(KEY_DATE) ?: 0L

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////

    companion object {
        private const val MAXIMUM_MONTHS = 12
        private const val MAXIMUM_YEARS = 100
        private const val STARTING_MONTH = 1
        private const val STARTING_DAY = 1

        const val KEY_DATE = "KEY_DATE"
        const val KEY_RESULT_DATE = "KEY_RESULT_DATE"
        const val KEY_RESULT_LISTENER = "KEY_RESULT_LISTENER"
    }
}
