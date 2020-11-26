/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.goals.ui.goals


import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.clearFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import app.chamich.feature.goals.R
import app.chamich.feature.goals.databinding.GoalsFragmentGoalsBinding
import app.chamich.feature.goals.model.api.IGoal
import app.chamich.feature.goals.utils.EXTRA_GOAL
import app.chamich.feature.goals.utils.REQUEST_KEY_EDIT_GOAL
import app.chamich.feature.goals.utils.REQUEST_KEY_GOAL_ACTIONS
import app.chamich.feature.goals.utils.REQUEST_KEY_GOAL_DETAILS
import app.chamich.library.core.CoreFragment
import app.chamich.library.core.model.Status
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
internal class GoalsFragment :
    CoreFragment<GoalsViewModel, GoalsFragmentGoalsBinding>(), GoalsAdapter.GoalsListener {

    private lateinit var adapter: GoalsAdapter

    //---- region Fragment Override Functions

    override fun getLayoutId() = R.layout.goals_fragment_goals

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            viewModel.loadGoals()
        }

        setupRecyclerView()
        setupObservers()

        // IMPROVEME: This is hacky solution and need to be fixed.
        findNavController().addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.destination_goals) {
                viewModel.loadGoals()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        // Show options menu when fragment became visible
        setHasOptionsMenu(true)
    }

    override fun onPause() {
        super.onPause()

        // Hide options menu when fragment become invisible
        setHasOptionsMenu(false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.goals_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_archive -> handleArchiveClicked()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getViewModelClass() = GoalsViewModel::class.java

    //---- endregion

    ////////////////////////////////////////////////////////////////////////////////////////////////

    //---- region GoalsAdapter.GoalsListener overridden methods

    override fun onGoalClicked(goal: IGoal) {
        navigateToGoalDetailsWithResult(goal)
    }

    override fun onActionsClicked(goal: IGoal) {
        setFragmentResultListener(REQUEST_KEY_GOAL_ACTIONS) { key, _ ->
            if (REQUEST_KEY_GOAL_ACTIONS == key) {
                clearFragmentResult(REQUEST_KEY_EDIT_GOAL)
            }
        }

        findNavController().navigate(
            R.id.destination_action_menu, bundleOf(EXTRA_GOAL to goal)
        )
    }

    //---- endregion

    ////////////////////////////////////////////////////////////////////////////////////////////////

    //---- region Private Functions

    private fun setupRecyclerView() {
        adapter = GoalsAdapter(this)
        binding.recyclerviewGoals.adapter = adapter
    }

    private fun setupObservers() {
        // Setup goals loading observer.
        viewModel.loadedGoals.observe(viewLifecycleOwner, { result ->
            when (result.status) {
                Status.SUCCESS -> handleSuccess(result.data)
                Status.LOADING -> handleLoading()
                Status.FAILURE -> handleFailure(result.exception)
            }
        })
    }

    private fun handleSuccess(goals: List<IGoal>?) {
        goals?.let { loadedGoals ->
            binding.linearLayoutNoGoalsPlaceholder.isVisible = loadedGoals.isEmpty()
            adapter.addGoals(loadedGoals)
        }
    }

    private fun handleLoading() {
        //
    }

    private fun handleFailure(exception: Exception?) {
        // No Exception can be thrown while loading goals from the database.
    }

    private fun handleArchiveClicked() {
        findNavController().navigate(R.id.destination_archive_home)
    }

    private fun navigateToGoalDetailsWithResult(goal: IGoal) {
        setFragmentResultListener(REQUEST_KEY_GOAL_DETAILS) { key, _ ->
            if (REQUEST_KEY_GOAL_DETAILS == key) {
                viewModel.loadGoals()
                clearFragmentResult(REQUEST_KEY_GOAL_DETAILS)
            }
        }

        findNavController().navigate(
            R.id.destination_goal_details,
            bundleOf(EXTRA_GOAL to goal)
        )
    }

    //---- endregion
}
