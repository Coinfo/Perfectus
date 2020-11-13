/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.goals.ui.archive

import app.chamich.feature.goals.R
import app.chamich.feature.goals.databinding.GoalsDialogFragmentGoalsArchiveBinding
import app.chamich.library.core.CoreDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class GoalsArchiveFragment :
    CoreDialogFragment<GoalsArchiveViewModel, GoalsDialogFragmentGoalsArchiveBinding>() {

    override fun getLayoutId() = R.layout.goals_dialog_fragment_goals_archive

    override fun getViewModelClass() = GoalsArchiveViewModel::class.java
}
