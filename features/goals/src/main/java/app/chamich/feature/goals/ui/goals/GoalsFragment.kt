/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.goals.ui.goals


import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import app.chamich.feature.goals.R
import app.chamich.feature.goals.databinding.GoalsFragmentGoalsBinding
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

        viewModel.getGoal()

        setupRecyclerView()
        setupObservers()
    }

    override fun getViewModelClass() = GoalsViewModel::class.java

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Private Functions

    private fun setupRecyclerView() {
        adapter = GoalsAdapter {

        }
        binding.recyclerviewGoals.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.getResult().observe(this, Observer { result ->
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
