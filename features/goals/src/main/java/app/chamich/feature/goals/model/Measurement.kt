/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.goals.model

import androidx.annotation.StringRes
import app.chamich.feature.goals.R

internal enum class Measurement(
    val id: Int, val position: Int, @StringRes val stringRes: Int
) {

    STEPS(0, 0, R.string.goals_measured_in_steps),
    HOURS(1, 1, R.string.goals_measured_in_hours),
    ITEMS(2, 2, R.string.goals_measured_in_items),
    PERCENT(3, 3, R.string.goals_measured_in_percent),
    DOLLARS(4, 4, R.string.goals_measured_in_dollars),
    EUROS(5, 5, R.string.goals_measured_in_euros),
    TIMES(6, 6, R.string.goals_measured_in_times),
    WEEKS(7, 7, R.string.goals_measured_in_weeks),
    DAYS(8, 8, R.string.goals_measured_in_days),
    LIBS(9, 9, R.string.goals_measured_in_libs),
    KG(10, 10, R.string.goals_measured_in_kg),
    MILES(11, 11, R.string.goals_measured_in_miles),
    KM(12, 12, R.string.goals_measured_in_km),
    BOOKS(13, 13, R.string.goals_measured_in_books),
    CHAPTERS(14, 14, R.string.goals_measured_in_chapters),
    PAGES(15, 15, R.string.goals_measured_in_pages),
    WORDS(16, 16, R.string.goals_measured_in_words),
    COURSES(17, 17, R.string.goals_measured_in_courses),
    SESSIONS(18, 18, R.string.goals_measured_in_sessions),
    CLASSES(19, 19, R.string.goals_measured_in_classes),
    VIDEOS(20, 20, R.string.goals_measured_in_videos);

    companion object {
        @JvmStatic
        fun asStringResource(id: Int) = values().asList().first { it.id == id }.stringRes
        fun asList() = values().asList().sortedBy { it.position }
        fun default() = HOURS
        fun valueOf(id: Int) = values()[id]
    }
}
