/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.library.database.api

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import app.chamich.library.database.entity.GoalEntity

@Dao
interface IGoalsDatabase {

    @Insert
    suspend fun add(goal: GoalEntity): Long

    @Update
    suspend fun update(goal: GoalEntity)

    @Query("DELETE FROM goals WHERE goal_id = :id")
    suspend fun delete(id: Long)

    @Query("SELECT * FROM goals WHERE goal_id == :id")
    suspend fun getGoal(id: Long): GoalEntity

    @Query("SELECT * FROM goals")
    suspend fun getGoals(): List<GoalEntity>
}
