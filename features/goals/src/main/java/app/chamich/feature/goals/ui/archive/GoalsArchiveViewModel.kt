/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.goals.ui.archive

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import app.chamich.feature.goals.repository.api.IRepository

internal class GoalsArchiveViewModel @ViewModelInject constructor(
    private val repository: IRepository
) : ViewModel()
