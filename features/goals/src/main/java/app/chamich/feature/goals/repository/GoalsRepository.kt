/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.goals.repository

import app.chamich.feature.goals.model.Goal
import app.chamich.feature.goals.model.api.IGoal
import app.chamich.feature.goals.repository.api.IRepository
import app.chamich.library.database.api.IGoalsDatabase
import app.chamich.library.database.api.IGoalsProgressDatabase
import app.chamich.library.database.entity.GoalEntity
import app.chamich.library.database.entity.GoalProgressEntity
import app.chamich.library.logger.ILogger


class GoalsRepository(
    private val goalsDatabase: IGoalsDatabase,
    private val goalProgressDatabase: IGoalsProgressDatabase,
    private val logger: ILogger,
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
        logger.debug(TAG, "|                Add Initial Goal and Progress               |")
        logger.debug(TAG, "|----> Goal Details: $goal")
        val goalId = goalsDatabase.add(goal.toGoalEntry())
        logger.debug(TAG, "|----> Goal Added With ID: $goalId")
        val goalProgressId = goalProgressDatabase.add(goal.toGoalProgressEntity(goalId = goalId))
        logger.debug(TAG, "|----> Goal Progress Added With ID: $goalProgressId")
        return goalId
    }

    override suspend fun getGoals(): List<IGoal> {
        logger.debug(TAG, "|------------------------------------------------------------|")
        logger.debug(TAG, "|                         Get Goals                          |")

        val goals = mutableListOf<IGoal>()
        goalsDatabase.getGoals().map { goalEntity -> goals.add(goalEntity.toGoal()) }
        logger.debug(TAG, "|----> Number of Goals: ${goals.size}")
        return goals
    }

    override suspend fun getGoal(id: Long): IGoal {
        logger.debug(TAG, "|------------------------------------------------------------|")
        logger.debug(TAG, "|                         Get Goal                           |")

        val goalEntity = goalsDatabase.getGoal(id)
        logger.debug(TAG, "|----> Goal is: $goalEntity")
        return goalEntity.toGoal()
    }

    override suspend fun updateGoal(goal: IGoal) {
        logger.debug(TAG, "|------------------------------------------------------------|")
        logger.debug(TAG, "|               Update Goal and Add Progress                 |")
        val goalEntity = goal.toGoalEntry()
        logger.debug(TAG, "|----> Updated goal data: $goalEntity")
        goalsDatabase.update(goalEntity)

        val goalProgressEntity = goal.toGoalProgressEntity(goalId = goal.id)
        logger.debug(TAG, "|----> Add goal progress: $goalProgressEntity")
        goalProgressDatabase.add(goalProgressEntity)
    }

    override suspend fun deleteGoal(id: Long) {
        logger.debug(TAG, "|------------------------------------------------------------|")
        logger.debug(TAG, "|                 Delete Goal and Progress                   |")
        logger.debug(TAG, "|----> Delete goal id: $id")
        goalsDatabase.delete(id)
        goalProgressDatabase.delete(id)
    }

    private fun IGoal.toGoalEntry() = GoalEntity(
        id = id,
        title = title,
        measuredIn = measuredIn,
        totalEffort = totalEffort,
        progress = progress,
        completeDate = completeData,
        category = category,
        color = color,
        creationDate = creationDate,
    )

    private fun GoalEntity.toGoal() = Goal(
        id = id,
        title = title,
        measuredIn = measuredIn,
        totalEffort = totalEffort,
        progress = progress,
        completeData = completeDate,
        category = category,
        color = color,
        creationDate = creationDate,
    )

    private fun IGoal.toGoalProgressEntity(goalId: Long) = GoalProgressEntity(
        id = 0L,
        goalId = goalId,
        progress = progress,
        date = System.currentTimeMillis(),
    )

    private companion object {
        const val TAG = "GoalsRepository"
    }
}
