/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.goals.ui.bottomsheet.menu

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.chamich.feature.goals.model.Goal
import app.chamich.feature.goals.model.GoalStatus
import app.chamich.feature.goals.model.api.IGoal
import app.chamich.feature.goals.repository.api.IRepository
import app.chamich.library.core.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class GoalActionsViewModel @ViewModelInject constructor(
    private val repository: IRepository
) : ViewModel() {

    private val _actionCompleted = MutableLiveData<Resource<Unit>>()
    val actionCompleted: LiveData<Resource<Unit>>
        get() = _actionCompleted

    fun deleteGoal(id: Long) {
        viewModelScope.launch {
            _actionCompleted.postValue(Resource.loading())
            withContext(Dispatchers.IO) {
                _actionCompleted.postValue(Resource.success(repository.deleteGoal(id)))
            }
        }
    }

    fun changeGoalStatus(goal: IGoal, status: GoalStatus) {
        viewModelScope.launch {
            _actionCompleted.postValue(Resource.loading())
            withContext(Dispatchers.IO) {
                val completeData =
                    if (status == GoalStatus.COMPLETED) System.currentTimeMillis()
                    else goal.completeData

                _actionCompleted.postValue(
                    Resource.success(
                        repository.updateGoal(
                            (goal as Goal).copy(status = status.id, completeData = completeData)
                        )
                    )
                )
            }
        }
    }
}
