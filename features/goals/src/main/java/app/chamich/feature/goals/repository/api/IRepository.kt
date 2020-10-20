/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.goals.repository.api

import app.chamich.feature.goals.model.api.IGoal

interface IRepository {

    /**
     * Adds goal to the database
     *
     * @return The id of the added gaol
     */
    suspend fun addGoal(goal: IGoal): Long

    /**
     * Gets all goals from the database
     *
     * @return List of goals
     */
    suspend fun getGoals(): List<IGoal>

    /**
     * Gets the goal by given id
     *
     * @param id Id of the goal
     * @return goal
     */
    suspend fun getGoal(id: Long): IGoal

    /**
     * Updates given goal
     *
     * @param goal Goal to be updated
     */
    suspend fun updateGoal(goal: IGoal)
}
