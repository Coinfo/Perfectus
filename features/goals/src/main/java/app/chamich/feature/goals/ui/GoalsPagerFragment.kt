/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.goals.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import app.chamich.feature.goals.R
import app.chamich.library.core.CorePagerFragment


// This fragment handles all navigation through the Goals functionality
class GoalsPagerFragment : CorePagerFragment() {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Fragment Override Functions

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.goals_container, container, false)

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region CorePagerFragment Override Functions

    override fun handleAddClicked() {
        // Navigates to the Add Goals fragment
        requireActivity().findNavController(
            R.id.nav_host_fragment_goals
        ).navigate(R.id.destination_add_goal)
    }

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////
}
