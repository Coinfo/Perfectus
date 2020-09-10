/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.goals.model

import androidx.annotation.StringRes
import app.chamich.feature.goals.R

internal enum class Category(
    val id: Int, val position: Int, @StringRes val stringRes: Int
) {
    NOT_SET(0, 0, R.string.goals_category_not_set),
    PHYSICAL_HEALTH(1, 1, R.string.goals_category_physical_health),
    FAMILY_AND_LOVE(2, 2, R.string.goals_category_family_and_love),
    FINANCIAL(3, 3, R.string.goals_category_financial),
    CREATIVITY_AND_FUN(4, 4, R.string.goals_category_creativity_and_fun),
    LEARNING(5, 5, R.string.goals_category_learning),
    CAREER_AND_IMPACT(6, 6, R.string.goals_catgeory_career_and_impact),
    FRIEND_AND_NETWORKING(7, 7, R.string.goals_category_friends_and_networking);

    companion object {
        fun asList() = values().asList().sortedBy { it.position }
        fun default() = NOT_SET
    }
}
