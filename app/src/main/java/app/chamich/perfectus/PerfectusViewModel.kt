/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.perfectus

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.chamich.library.authentication.IUser
import app.chamich.library.cloud.CloudProfile
import app.chamich.library.cloud.api.Cloud
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PerfectusViewModel @ViewModelInject constructor(
    private val cloud: Cloud
) : ViewModel() {

    fun createCloudProfile(user: IUser) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                cloud.createCloudProfile(
                    CloudProfile(
                        user.uid,
                        user.email,
                        user.displayName,
                        user.photoUrl.toString()
                    )
                )
            }
        }
    }
}
