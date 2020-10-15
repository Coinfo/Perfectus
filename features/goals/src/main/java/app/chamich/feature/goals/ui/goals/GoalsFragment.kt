/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.goals.ui.goals


import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import app.chamich.feature.goals.R
import app.chamich.feature.goals.databinding.GoalsFragmentGoalsBinding
import app.chamich.feature.goals.ui.details.GoalDetailsDialogFragment
import app.chamich.library.core.CoreFragment
import app.chamich.library.core.model.Status
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
internal class GoalsFragment :
    CoreFragment<GoalsViewModel, GoalsFragmentGoalsBinding>() {

    private lateinit var adapter: GoalsAdapter

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Fragment Override Functions

    override fun getLayoutId() = R.layout.goals_fragment_goals

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            viewModel.loadGoals()
        }

        setupRecyclerView()
        setupObservers()
    }

    override fun getViewModelClass() = GoalsViewModel::class.java

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Private Functions

    private fun setupRecyclerView() {
        adapter = GoalsAdapter { id ->
            findNavController().navigate(
                R.id.destination_goal, bundleOf(
                    GoalDetailsDialogFragment.EXTRA_GOAL_ID to id
                )
            )
        }
        binding.recyclerviewGoals.adapter = adapter

    }

    private fun setupObservers() {
        viewModel.getResult().observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Status.SUCCESS -> {
                    result.data?.let {
                        adapter.addGoals(it)
                    }
                }
            }
        })
    }

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////
}
