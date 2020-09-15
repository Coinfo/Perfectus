/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.goals.ui.bottomsheet.colors

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import app.chamich.feature.goals.R
import app.chamich.feature.goals.databinding.GoalsBottomSheetDialogColorsBinding
import app.chamich.feature.goals.model.Color
import app.chamich.feature.goals.ui.bottomsheet.GoalsBottomSheet

internal class ColorsBottomSheet :
    GoalsBottomSheet<GoalsBottomSheetDialogColorsBinding>() {

    private lateinit var selectedColor: Color

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Fragment Override Functions

    override fun getLayoutId() = R.layout.goals_bottom_sheet_dialog_colors

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBindings()
        setupChips(getColorId())
    }

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Binding Functions

    fun onUseColorClicked() {
        setFragmentResult(KEY_RESULT_LISTENER, bundleOf(KEY_RESULT_COLOR to selectedColor))
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

    private fun setupChips(colorId: Int) =
        Color.asList().forEach { color ->
            binding.chipGroupColors.addView(
                inflateAsChip(R.layout.goals_chip_color, binding.chipGroupColors).apply {
                    id = ViewCompat.generateViewId()
                    chipBackgroundColor = getColor(color.colorRes)
                    setOnClickListener { selectedColor = color }
                    setOnCheckedChangeListener { chip, checked ->
                        chip.setText(getCheckedText(checked))
                    }
                    isChecked = color.id == colorId
                })
        }

    private fun getColor(@ColorRes color: Int) =
        ColorStateList.valueOf(ContextCompat.getColor(requireContext(), color))

    private fun getColorId() = arguments?.getInt(KEY_COLOR_ID) ?: Color.default().id

    private fun getCheckedText(checked: Boolean) =
        if (checked) R.string.goals_colors_text_check_mark else R.string.goals_colors_text_empty

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////

    companion object {
        const val KEY_COLOR_ID = "KEY_COLOR_ID"
        const val KEY_RESULT_COLOR = "KEY_RESULT_COLOR"
        const val KEY_RESULT_LISTENER = "KEY_RESULT_LISTENER"
    }
}
