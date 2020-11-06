/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.goals.ui.modify.edit

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

internal class EditGoalViewModel @ViewModelInject constructor(
    private val repository: IRepository
) : ModifyGoalViewModel() {

    private val _loadedGoal = MutableLiveData<Resource<IGoal>>()
    var loadedGoal: LiveData<Resource<IGoal>> = _loadedGoal

    var initialGoal: IGoal? = null

    fun loadGoal(id: Long) {
        viewModelScope.launch {
            _loadedGoal.postValue(Resource.loading())
            withContext(Dispatchers.IO) {
                _loadedGoal.postValue(Resource.success(repository.getGoal(id)))
            }
        }
    }
}
