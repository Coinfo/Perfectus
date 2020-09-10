/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.goals.ui.bottomsheet.categories

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import app.chamich.feature.goals.R
import app.chamich.feature.goals.databinding.GoalsBottomSheetDialogCategoriesBinding
import app.chamich.feature.goals.model.Category
import app.chamich.feature.goals.ui.bottomsheet.GoalsBottomSheet
import app.chamich.feature.goals.ui.bottomsheet.measurements.MeasurementsBottomSheet

internal class CategoriesBottomSheet :
    GoalsBottomSheet<ViewModel, GoalsBottomSheetDialogCategoriesBinding>() {

    private lateinit var selectedCategory: Category

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Fragment Override Functions

    override fun getLayoutId() = R.layout.goals_bottom_sheet_dialog_categories

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBindings()
        setupChips(getCategoryId())
    }

    override fun getViewModelClass() = ViewModel::class.java

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Binding Functions

    fun onUseMeasurementClicked() {
        setFragmentResult(
            MeasurementsBottomSheet.KEY_RESULT_LISTENER,
            bundleOf(KEY_RESULT_CATEGORY to selectedCategory)
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

    private fun setupChips(categoryId: Int) =
        Category.asList().forEach { category ->
            binding.chipGroupCategory.addView(
                inflateAsChip(R.layout.goals_chip_category, binding.chipGroupCategory).apply {
                    id = ViewCompat.generateViewId()
                    isChecked = categoryId == category.id
                    setText(category.stringRes)
                    setOnClickListener { selectedCategory = category }
                })
        }

    private fun getCategoryId() =
        arguments?.getInt(KEY_CATEGORY_ID) ?: Category.default().id

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////

    companion object {
        const val KEY_CATEGORY_ID = "KEY_CATEGORY_ID"
        const val KEY_RESULT_CATEGORY = "KEY_RESULT_CATEGORY"
        const val KEY_RESULT_LISTENER = "KEY_RESULT_LISTENER"
    }
}
