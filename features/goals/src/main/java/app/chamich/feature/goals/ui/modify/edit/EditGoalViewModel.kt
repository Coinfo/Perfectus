/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.goals.ui.modify.edit

import androidx.hilt.lifecycle.ViewModelInject
import app.chamich.feature.goals.repository.api.IRepository
import app.chamich.feature.goals.ui.modify.ModifyGoalViewModel

internal class EditGoalViewModel @ViewModelInject constructor(
    private val repository: IRepository
) : ModifyGoalViewModel()
