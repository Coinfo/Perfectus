/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.goals.ui.goals


import app.chamich.feature.goals.R
import app.chamich.feature.goals.databinding.GoalsFragmentGoalsBinding
import app.chamich.library.core.CoreFragment

internal class GoalsFragment :
    CoreFragment<GoalsViewModel, GoalsFragmentGoalsBinding>() {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Fragment Override Functions

    override fun getLayoutId() = R.layout.goals_fragment_goals

    override fun getViewModelClass() = GoalsViewModel::class.java

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Public Functions


    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////
}
