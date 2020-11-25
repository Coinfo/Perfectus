/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.friends.ui.add

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.chamich.library.cloud.api.Cloud
import app.chamich.library.cloud.api.Profile
import app.chamich.library.cloud.exception.CloudException
import app.chamich.library.core.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class AddFriendsViewModel @ViewModelInject constructor(
    private val cloud: Cloud
) : ViewModel() {

    private val _foundProfile = MutableLiveData<Resource<Profile>>()
    val foundProfile: LiveData<Resource<Profile>>
        get() = _foundProfile

    fun searchProfile(email: String) {
        viewModelScope.launch {
            _foundProfile.postValue(Resource.loading())
            withContext(Dispatchers.IO) {
                try {
                    _foundProfile.postValue(Resource.success(cloud.searchProfile(email)))
                } catch (exception: CloudException) {
                    _foundProfile.postValue(Resource.error(exception))
                }
            }
        }
    }

}
