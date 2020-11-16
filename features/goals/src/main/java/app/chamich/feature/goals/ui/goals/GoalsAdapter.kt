/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.goals.ui.goals

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import app.chamich.feature.goals.R
import app.chamich.feature.goals.databinding.GoalsItemGoalBinding
import app.chamich.feature.goals.databinding.GoalsItemHeaderBinding
import app.chamich.feature.goals.model.Category
import app.chamich.feature.goals.model.api.IGoal


internal class GoalsAdapter(
    private val listener: (Long) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items: MutableList<GoalUi> = mutableListOf()

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Override Functions

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            VIEW_TYPE_GOAL -> GoalsViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.goals_item_goal,
                    parent,
                    false
                )
            )
            VIEW_TYPE_HEADER -> HeaderViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.goals_item_header,
                    parent,
                    false
                )
            )
            else -> throw IllegalStateException("Unknown view type provided.")
        }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is GoalsViewHolder -> {
                val goal = items[position]
                holder.binding.goal = goal
                holder.binding.root.setOnClickListener { listener(goal.id) }
            }
            is HeaderViewHolder -> {
                val header = items[position]
                holder.binding.textViewTitle.setText(Category.valueOf(header.category).stringRes)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position].isHeader) VIEW_TYPE_HEADER else VIEW_TYPE_GOAL
    }

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Public Functions

    fun addGoals(goals: List<IGoal>) {
        val categoriesMap = Category.asList().map { it.id to false }.toMap().toMutableMap()

        this.items.clear()
        goals.sortedBy { it.category }.forEach {
            if (categoriesMap[it.category] == false && it.category != Category.NOT_SET.id) {
                this.items.add(GoalUi(it, true))
                this.items.add(GoalUi(it, false))
                categoriesMap[it.category] = true
            } else {
                this.items.add(GoalUi(it, false))
            }
        }
        this.notifyDataSetChanged()
    }

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////

    inner class GoalsViewHolder(
        val binding: GoalsItemGoalBinding
    ) : RecyclerView.ViewHolder(binding.root)

    inner class HeaderViewHolder(
        val binding: GoalsItemHeaderBinding
    ) : RecyclerView.ViewHolder(binding.root)

    data class GoalUi(val goal: IGoal, val isHeader: Boolean) : IGoal by goal

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_GOAL = 1
    }
}
