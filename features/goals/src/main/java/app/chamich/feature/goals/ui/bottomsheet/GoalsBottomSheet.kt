/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.goals.ui.bottomsheet

import android.os.Bundle
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import app.chamich.feature.goals.R
import app.chamich.library.core.CoreBottomSheetDialogFragment
import com.google.android.material.chip.Chip

internal abstract class GoalsBottomSheet<VM : ViewModel, VDB : ViewDataBinding> :
    CoreBottomSheetDialogFragment<VM, VDB>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Perfectus_BottomSheetDialog)
    }

    protected fun inflateAsChip(@LayoutRes layoutRes: Int, parent: ViewGroup) =
        layoutInflater.inflate(layoutRes, parent, false) as Chip
}
