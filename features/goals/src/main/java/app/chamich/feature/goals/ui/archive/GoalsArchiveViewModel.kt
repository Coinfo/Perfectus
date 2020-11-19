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
    val archivedGoals: LiveData<Resource<List<IGoal>>>
        get() = _archivedGoals

    private val _updatedGoal = MutableLiveData<Resource<Unit>>()
    val updatedGoal: LiveData<Resource<Unit>>
        get() = _updatedGoal

    var status = GoalStatus.ARCHIVED

    fun loadArchivedGoals() {
        viewModelScope.launch {
            _archivedGoals.postValue(Resource.loading())
            withContext(Dispatchers.IO) {
                _archivedGoals.postValue(Resource.success(repository.getGoals(status)))
            }
        }
    }

    fun updateGoal(goal: IGoal) {
        viewModelScope.launch {
            _updatedGoal.postValue(Resource.loading())
            withContext(Dispatchers.IO) {
                _updatedGoal.postValue(Resource.success(repository.updateGoal(goal)))
            }
        }
    }
}
