/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.library.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import app.chamich.library.database.utils.Constants

@Entity(tableName = Constants.TABLE_NAME_GOALS)
data class GoalEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constants.COLUMN_GOAL_ID)
    val id: Long,

    @ColumnInfo(name = Constants.COLUMN_GOAL_TITLE)
    val title: String,

    @ColumnInfo(name = Constants.COLUMN_GOAL_MEASURED_IN)
    val measuredIn: Int,

    @ColumnInfo(name = Constants.COLUMN_GOAL_TOTAL_EFFORT)
    val totalEffort: Int,

    @ColumnInfo(name = Constants.COLUMN_GOAL_PROGRESS)
    val progress: Int,

    @ColumnInfo(name = Constants.COLUMN_GOAL_COMPLETE_DATE)
    val completeDate: Long,

    @ColumnInfo(name = Constants.COLUMN_GOAL_CATEGORY)
    val category: Int,

    @ColumnInfo(name = Constants.COLUMN_GOAL_COLOR)
    val color: Int,

    @ColumnInfo(name = Constants.COLUMN_GOAL_CREATION_DATE)
    val creationDate: Long,
)
