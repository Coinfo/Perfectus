/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.goals.ui.modify.add

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import app.chamich.feature.goals.model.api.IGoal
import app.chamich.feature.goals.repository.api.IRepository
import app.chamich.feature.goals.ui.modify.ModifyGoalViewModel
import app.chamich.library.core.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


internal class AddGoalViewModel @ViewModelInject constructor(
    private val repository: IRepository
) : ModifyGoalViewModel() {

    private val result = MutableLiveData<Resource<Long>>()

    fun addGoal(goal: IGoal) {
        viewModelScope.launch {
            result.postValue(Resource.loading(null))
            withContext(Dispatchers.IO) {
                result.postValue(Resource.success(repository.addGoal(goal)))
            }
        }
    }

    fun getResult(): LiveData<Resource<Long>> = result
}

