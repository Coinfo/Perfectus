/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.goals.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import app.chamich.feature.goals.R
import app.chamich.library.core.CorePagerFragment
import dagger.hilt.android.AndroidEntryPoint


// This fragment handles all navigation through the Goals functionality
@AndroidEntryPoint
class GoalsPagerFragment : CorePagerFragment() {

    private var viewModel: GoalsPagerViewModel? = null

    private var titleView: TextView? = null

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Fragment Override Functions

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.goals_container, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(GoalsPagerViewModel::class.java)
        titleView?.text = viewModel?.getDisplayedName() ?: ""
    }

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

    override fun setTitleView(view: TextView) {
        titleView = view
        titleView?.text = viewModel?.getDisplayedName() ?: ""
    }

    override fun getSubtitle(): String {
        return "It's time to set the goal"
    }

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////
}
