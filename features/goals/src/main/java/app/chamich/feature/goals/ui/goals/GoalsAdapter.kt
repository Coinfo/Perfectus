/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.goals.ui.goals

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.annotation.ColorRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import app.chamich.feature.goals.R
import app.chamich.feature.goals.databinding.GoalsItemGoalBinding
import app.chamich.feature.goals.model.Color
import app.chamich.feature.goals.model.api.IGoal
import app.chamich.library.core.extensions.getColorAsInt


internal class GoalsAdapter(
    private val listener: (Long) -> Unit,
    private val context: Context
) : RecyclerView.Adapter<GoalsAdapter.GoalsViewHolder>() {

    private val goals: MutableList<IGoal> = mutableListOf()

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Override Functions

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalsViewHolder =
        GoalsViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.goals_item_goal,
                parent,
                false
            )
        )

    override fun getItemCount() = goals.size

    override fun onBindViewHolder(holder: GoalsViewHolder, position: Int) {
        val goal = goals[position]
        holder.binding.goal = goal
        holder.binding.root.setOnClickListener { listener(goal.id) }
        setProgressBarColor(holder.binding.progressBarProgress, Color.asColorResource(goal.color))

    }

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Public Functions

    fun addGoals(goals: List<IGoal>) {
        this.goals.clear()
        this.goals.addAll(goals)
        this.notifyDataSetChanged()
    }

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Private Functions

    private fun setProgressBarColor(progressBar: ProgressBar, @ColorRes colorRes: Int) {
        progressBar.progressTintList = ColorStateList.valueOf(context.getColorAsInt(colorRes))
    }

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////

    class GoalsViewHolder(
        val binding: GoalsItemGoalBinding
    ) : RecyclerView.ViewHolder(binding.root)
}
