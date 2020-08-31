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

    @ColumnInfo(name = "goal_type")
    val type: Int,

    @ColumnInfo(name = "goal_color")
    val color: Int
)
