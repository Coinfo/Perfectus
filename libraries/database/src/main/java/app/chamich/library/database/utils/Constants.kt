/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.library.database.utils


internal object Constants {
    // Tables
    const val TABLE_NAME_GOALS = "goals"
    const val TABLE_NAME_GOALS_PROGRESS = "goals_progress"

    // Goals columns
    const val COLUMN_GOAL_ID = "goal_id"
    const val COLUMN_GOAL_TITLE = "goal_title"
    const val COLUMN_GOAL_MEASURED_IN = "goal_measured_in"
    const val COLUMN_GOAL_TOTAL_EFFORT = "goal_total_effort"
    const val COLUMN_GOAL_PROGRESS = "goal_progress"
    const val COLUMN_GOAL_COMPLETE_DATE = "goal_complete_date"
    const val COLUMN_GOAL_CATEGORY = "goal_category"
    const val COLUMN_GOAL_COLOR = "goal_color"

    // Goal progress columns
    const val COLUMN_GOAL_PROGRESS_ID = "goal_progress_id"
    const val COLUMN_GOAL_PROGRESS_ADDED_PROGRESS = "goal_progress_added_progress"
    const val COLUMN_GOAL_PROGRESS_ADDED_DATE = "goal_progress_added_date"
}
