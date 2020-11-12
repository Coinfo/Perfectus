/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.library.database.utils


internal object Constants {
    // Tables
    const val TABLE_NAME_GOALS = "goals"
    const val TABLE_NAME_GOALS_PROGRESS = "goals_progress"

    // Goals columns
    const val COLUMN_GOAL_ID = "id"
    const val COLUMN_GOAL_TITLE = "title"
    const val COLUMN_GOAL_MEASURED_IN = "measured_in"
    const val COLUMN_GOAL_TOTAL_EFFORT = "total_effort"
    const val COLUMN_GOAL_PROGRESS = "progress"
    const val COLUMN_GOAL_COMPLETE_DATE = "complete_date"
    const val COLUMN_GOAL_CATEGORY = "category"
    const val COLUMN_GOAL_COLOR = "color"
    const val COLUMN_GOAL_CREATION_DATE = "creation_date"
    const val COLUMN_GOAL_STATUS = "status"

    // Goal progress columns
    const val COLUMN_GOAL_PROGRESS_ID = "id"
    const val COLUMN_GOAL_PROGRESS_GOAL_ID = "goal_id"
    const val COLUMN_GOAL_PROGRESS_CURRENT_PROGRESS = "progress"
    const val COLUMN_GOAL_PROGRESS_ADDED_DATE = "date"
}
