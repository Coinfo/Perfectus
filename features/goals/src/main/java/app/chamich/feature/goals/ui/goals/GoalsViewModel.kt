/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.goals.ui.goals

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

internal class GoalsViewModel @ViewModelInject constructor(
    private val repository: IRepository
) : ViewModel() {

    private val _loadedGoals = MutableLiveData<Resource<List<IGoal>>>()
    var loadedGoals: LiveData<Resource<List<IGoal>>> = _loadedGoals

    fun loadGoals() {
        viewModelScope.launch {
            _loadedGoals.postValue(Resource.loading(null))
            withContext(Dispatchers.IO) {
                _loadedGoals.postValue(Resource.success(repository.getGoals(GoalStatus.ACTIVE)))
            }
        }
    }
}

