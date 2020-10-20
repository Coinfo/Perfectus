/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.goals.repository

import app.chamich.feature.goals.model.Goal
import app.chamich.feature.goals.model.api.IGoal
import app.chamich.feature.goals.repository.api.IRepository
import app.chamich.library.database.api.IGoalsDatabase
import app.chamich.library.database.entity.GoalEntity
import app.chamich.library.logger.ILogger


class GoalsRepository(
    private val database: IGoalsDatabase,
    private val logger: ILogger
) : IRepository {

    init {
        // IMPROVEME: GoalsRepository class is created as a Singleton and lives through the application
        // lifecycle,  maybe better  way would be to create it in the  @FragmentScope, this  need to be
        // investigated and changed if needed.
        logger.debug(TAG, "|------------------------------------------------------------|")
        logger.debug(TAG, "|                      Goals Repository                      |")
        logger.debug(TAG, "|----> Goals Repository ID: ${System.identityHashCode(this)}")
    }

    override suspend fun addGoal(goal: IGoal): Long {
        logger.debug(TAG, "|------------------------------------------------------------|")
        logger.debug(TAG, "|                          Add Goal                          |")
        logger.debug(TAG, "|----> Goal Details: $goal")
        val id = database.add(goal.toGoalEntry())
        logger.debug(TAG, "|----> Goal Added With ID: $id")
        return id
    }

    override suspend fun getGoals(): List<IGoal> {
        logger.debug(TAG, "|------------------------------------------------------------|")
        logger.debug(TAG, "|                         Get Goals                          |")

        val goals = mutableListOf<IGoal>()
        database.getGoals().map { goalEntity -> goals.add(goalEntity.toGoal()) }
        logger.debug(TAG, "|----> Number of Goals: ${goals.size}")
        return goals
    }

    override suspend fun getGoal(id: Long): IGoal {
        logger.debug(TAG, "|------------------------------------------------------------|")
        logger.debug(TAG, "|                         Get Goal                           |")

        val goalEntity = database.getGoal(id)
        logger.debug(TAG, "|----> Goal is: $goalEntity")
        return goalEntity.toGoal()
    }

    override suspend fun updateGoal(goal: IGoal) {
        logger.debug(TAG, "|------------------------------------------------------------|")
        logger.debug(TAG, "|                       Update Goal                          |")
        val goalEntity = goal.toGoalEntry()
        logger.debug(TAG, "|----> Updated goal data: $goalEntity")

        database.update(goalEntity)
    }

    private fun IGoal.toGoalEntry() = GoalEntity(
        id = id,
        title = title,
        measuredIn = measuredIn,
        totalEffort = totalEffort,
        progress = progress,
        completeDate = completeData,
        category = category,
        color = color
    )

    private fun GoalEntity.toGoal() = Goal(
        id = id,
        title = title,
        measuredIn = measuredIn,
        totalEffort = totalEffort,
        progress = progress,
        completeData = completeDate,
        category = category,
        color = color
    )

    private companion object {
        const val TAG = "GoalsRepository"
    }
}
