/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.goals.ui.archive

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.chamich.feature.goals.model.GoalStatus
import app.chamich.feature.goals.model.api.IGoal
import app.chamich.feature.goals.repository.api.IRepository
import app.chamich.library.core.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class GoalsArchiveViewModel @ViewModelInject constructor(
    private val repository: IRepository
) : ViewModel() {

    private val _archivedGoals = MutableLiveData<Resource<List<IGoal>>>()
    var archivedGoals: LiveData<Resource<List<IGoal>>> = _archivedGoals

    var status = GoalStatus.ARCHIVED

    fun loadArchivedGoals() {
        viewModelScope.launch {
            _archivedGoals.postValue(Resource.loading(null))
            withContext(Dispatchers.IO) {
                _archivedGoals.postValue(Resource.success(repository.getGoals(status)))
            }
        }
    }
}
