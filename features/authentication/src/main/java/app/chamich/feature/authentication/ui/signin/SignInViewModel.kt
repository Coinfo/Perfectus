/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.authentication.ui.signin

import android.content.Context
import android.content.Intent
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.chamich.library.authentication.IAuthenticator
import app.chamich.library.authentication.IUser
import app.chamich.library.core.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class SignInViewModel @ViewModelInject constructor(
    private val authenticator: IAuthenticator,
) : ViewModel() {

    private val _user = MutableLiveData<Resource<IUser>>()
    val user: LiveData<Resource<IUser>>
        get() = _user

    //---- region Public Functions

    fun getGoogleSignInIntent(context: Context) = authenticator.getGoogleSignInIntent(context)

    fun handleGoogleSignIn(intent: Intent?) {
        viewModelScope.launch {
            _user.postValue(Resource.loading(null))
            withContext(Dispatchers.IO) {
                _user.postValue(Resource.success(authenticator.handleGoogleSignIn(intent)))
            }
        }
    }

    //---- endregion
}
