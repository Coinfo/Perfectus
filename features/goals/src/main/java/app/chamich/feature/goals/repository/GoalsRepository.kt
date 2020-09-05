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
        val id = database.add(
            GoalEntity(
                title = goal.title,
                measuredIn = goal.measuredIn,
                totalEffort = goal.totalEffort,
                progress = goal.progress,
                completeDate = goal.completeData,
                category = goal.category,
                color = goal.color
            )
        )
        logger.debug(TAG, "|----> Goal Added With ID: $id")
        return id
    }

    override suspend fun getGoals(): List<IGoal> {
        logger.debug(TAG, "|------------------------------------------------------------|")
        logger.debug(TAG, "|                         Get Goals                          |")

        val goals = mutableListOf<IGoal>()
        database.getGoals().map {
            goals.add(
                Goal(
                    id = it.id,
                    title = it.title,
                    measuredIn = it.measuredIn,
                    totalEffort = it.totalEffort,
                    progress = it.progress,
                    completeData = it.completeDate,
                    category = it.category,
                    color = it.color
                )
            )
        }
        logger.debug(TAG, "|----> Number of Goals: ${goals.size}")
        return goals
    }

    private companion object {
        const val TAG = "GoalsRepository"
    }
}
