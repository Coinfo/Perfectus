/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.library.database.api

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import app.chamich.library.database.entity.GoalProgressEntity

@Dao
interface IGoalsProgressDatabase {

    @Insert
    suspend fun add(goalProgress: GoalProgressEntity): Long

    @Query("DELETE FROM goals_progress WHERE goal_id == :id")
    suspend fun delete(id: Long)
}
