/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.goals.model

internal enum class GoalStatus(val id: Int) {
    IN_PROGRESS(0),
    COMPLETED(1),
    ARCHIVED(2),
}
