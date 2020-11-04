/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.library.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import app.chamich.library.database.utils.Constants

@Entity(tableName = Constants.TABLE_NAME_GOALS_PROGRESS)
data class GoalProgressEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constants.COLUMN_GOAL_PROGRESS_ID)
    val id: Long,

    @ColumnInfo(name = Constants.COLUMN_GOAL_PROGRESS_ADDED_PROGRESS)
    val progress: Int,

    @ColumnInfo(name = Constants.COLUMN_GOAL_PROGRESS_ADDED_DATE)
    val date: Long,
)
