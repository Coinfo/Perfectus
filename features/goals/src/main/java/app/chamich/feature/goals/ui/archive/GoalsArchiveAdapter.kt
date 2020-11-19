/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.goals.ui.archive

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import app.chamich.feature.goals.R
import app.chamich.feature.goals.databinding.GoalsItemArchivedGoalBinding
import app.chamich.feature.goals.model.api.IGoal
import app.chamich.feature.goals.ui.archive.GoalsArchiveAdapter.ArchivedGoalsViewHolder

internal class GoalsArchiveAdapter(
    private val listener: GoalsArchiveListener
) : RecyclerView.Adapter<ArchivedGoalsViewHolder>() {

    private val goals: MutableList<IGoal> = mutableListOf()

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Override Functions

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ArchivedGoalsViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.goals_item_archived_goal,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ArchivedGoalsViewHolder, position: Int) {
        val goal = goals[position]
        holder.binding.goal = goal
        holder.binding.root.setOnClickListener { listener.onGoalClicked(goal) }
        holder.binding.buttonGoalActions.setOnClickListener { listener.onActionsClicked(goal) }
    }

    override fun getItemCount() = goals.size

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Public Functions

    fun addArchivedGoals(goals: List<IGoal>) {
        this.goals.clear()
        this.goals.addAll(goals)
        this.notifyDataSetChanged()
    }

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////

    inner class ArchivedGoalsViewHolder(
        val binding: GoalsItemArchivedGoalBinding
    ) : RecyclerView.ViewHolder(binding.root)

    interface GoalsArchiveListener {
        fun onGoalClicked(goal: IGoal)
        fun onActionsClicked(goal: IGoal)
    }
}
