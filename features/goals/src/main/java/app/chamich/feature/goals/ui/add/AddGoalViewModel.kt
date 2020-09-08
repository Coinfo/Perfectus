/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.goals.ui.add

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.chamich.feature.goals.model.Color
import app.chamich.feature.goals.model.Measurement
import app.chamich.feature.goals.model.api.IGoal
import app.chamich.feature.goals.repository.api.IRepository
import app.chamich.library.core.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


internal class AddGoalViewModel @ViewModelInject constructor(
    private val repository: IRepository
) : ViewModel() {

    private val result = MutableLiveData<Resource<Long>>()
    var measurement: Measurement = Measurement.defaultMeasurement()
    var color: Color = Color.defaultColor()

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

