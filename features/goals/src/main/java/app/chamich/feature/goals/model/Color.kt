/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.goals.model

import androidx.annotation.ColorRes
import app.chamich.feature.goals.R

internal enum class Color(
    val id: Int, val position: Int, @ColorRes val colorRes: Int
) {

    BLUE(0, 0, R.color.goals_color_blue),
    GREEN(1, 1, R.color.goals_color_green),
    ORANGE(2, 2, R.color.goals_color_orange),
    PURPLE(3, 3, R.color.goals_color_purple),
    RED(4, 4, R.color.goals_color_red),
    YELLOW(5, 5, R.color.goals_color_yellow);

    companion object {
        fun asList() = values().asList().sortedBy { it.position }
        fun default() = BLUE
    }
}
