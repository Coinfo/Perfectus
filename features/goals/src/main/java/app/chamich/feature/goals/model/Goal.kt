/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.goals.model

import android.os.Parcelable
import app.chamich.feature.goals.model.api.IGoal
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Goal(
    override val id: Long = 0L,
    override val title: String,
    override val measuredIn: Int,
    override val totalEffort: Int,
    override val progress: Int,
    override val completeData: Long,
    override val category: Int,
    override val color: Int,
    override val creationDate: Long,
    override val status: Int
) : IGoal, Parcelable
