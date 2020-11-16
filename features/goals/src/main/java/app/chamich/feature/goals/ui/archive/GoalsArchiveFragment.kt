/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.goals.ui.archive

import android.os.Bundle
import android.view.ViewGroup
import app.chamich.feature.goals.R
import app.chamich.feature.goals.databinding.GoalsDialogFragmentArchiveBinding
import app.chamich.library.core.CoreDialogFragment
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

    override fun getViewModelClass() = GoalsArchiveViewModel::class.java

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////
}
