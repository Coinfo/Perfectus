/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.goals.model.api

interface IGoal {
    val id: Long
    val title: String
    val measuredIn: Int
    val totalEffort: Int
    val progress: Int
    val completeData: Long
    val category: Int
    val color: Int
}
