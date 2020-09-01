/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.goals.ui.add

import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import app.chamich.feature.goals.R
import app.chamich.feature.goals.databinding.GoalsDialogFragmentAddGoalBinding
import app.chamich.library.core.CoreDialogFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
internal class AddGoalDialogFragment :
    CoreDialogFragment<AddGoalViewModel, GoalsDialogFragmentAddGoalBinding>() {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Fragment Override Functions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NORMAL, R.style.Perfectus_FullScreenDialog)

        // Handle back button clicks.
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            })
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

    override fun getLayoutId() = R.layout.goals_dialog_fragment_add_goal


    override fun getViewModelClass() = AddGoalViewModel::class.java

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////

}
