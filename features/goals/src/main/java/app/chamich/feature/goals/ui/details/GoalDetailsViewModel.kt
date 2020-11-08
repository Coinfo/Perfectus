/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.goals.ui.details

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.chamich.feature.goals.model.api.IGoal
import app.chamich.feature.goals.repository.api.IRepository
import app.chamich.library.core.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class GoalDetailsViewModel @ViewModelInject constructor(
    private val repository: IRepository
) : ViewModel() {

    private val goalUpdateResult = MutableLiveData<Resource<Unit>>()
    private val goalLoadResult = MutableLiveData<Resource<IGoal>>()

     var initialGoal: IGoal? = null

    private val progress = MutableLiveData(0)

    fun loadGoal(id: Long) {
        viewModelScope.launch {
            goalLoadResult.postValue(Resource.loading(null))
            withContext(Dispatchers.IO) {
                initialGoal = repository.getGoal(id)
                goalLoadResult.postValue(Resource.success(initialGoal))
            }
        }
    }

    fun updateGoal(goal: IGoal) {
        goalUpdateResult.postValue(Resource.loading())
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.updateGoal(goal)
                goalUpdateResult.postValue(Resource.success(null))
            }
        }
    }

    fun getLoadGoalResult(): LiveData<Resource<IGoal>> = goalLoadResult

    fun getUpdateGoalResult(): LiveData<Resource<Unit>> = goalUpdateResult

    fun getProgress(): LiveData<Int> = progress

    fun lessProgress() {
        progress.postValue(progress.value?.minus(1))
    }

    fun moreProgress() {
        progress.postValue(progress.value?.plus(1))
    }
}
