/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.library.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "goals")
data class GoalEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "goal_id")
    val id: Long = 0L,

    @ColumnInfo(name = "goal_title")
    val title: String,

    @ColumnInfo(name = "goal_measured_in")
    val measuredIn: Int,

    @ColumnInfo(name = "goal_total_effort")
    val totalEffort: Long,

    @ColumnInfo(name = "goal_progress")
    val progress: Long,

    @ColumnInfo(name = "goal_complete_date")
    val completeDate: Long,

    @ColumnInfo(name = "goal_category")
    val category: Int,

    @ColumnInfo(name = "goal_color")
    val color: Int
)
