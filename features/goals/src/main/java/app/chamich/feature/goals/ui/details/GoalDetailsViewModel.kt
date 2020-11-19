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

    private val _deleteGoal = MutableLiveData<Resource<Unit>>()
    var deleteGoal: LiveData<Resource<Unit>> = _deleteGoal

    private val _updateGoal = MutableLiveData<Resource<Unit>>()
    var updateGoal: LiveData<Resource<Unit>> = _updateGoal


    var initialGoal: IGoal? = null

    private val progress = MutableLiveData(0)

    fun updateGoal(goal: IGoal) = viewModelScope.launch {
        _updateGoal.postValue(Resource.loading())
        withContext(Dispatchers.IO) {
            _updateGoal.postValue(Resource.success(repository.updateGoal(goal)))
        }
    }

    fun deleteGoal(id: Long) = viewModelScope.launch {
        _deleteGoal.postValue(Resource.loading())
        withContext(Dispatchers.IO) {
            _deleteGoal.postValue(Resource.success(repository.deleteGoal(id)))
        }
    }


    fun getProgress(): LiveData<Int> = progress

    fun lessProgress() {
        progress.postValue(progress.value?.minus(1))
    }

    fun moreProgress() {
        progress.postValue(progress.value?.plus(1))
    }
}
