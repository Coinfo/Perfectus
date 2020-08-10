package app.chamich.feature.authentication.ui.signup

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.chamich.feature.authentication.model.Resource
import app.chamich.library.authentication.IAuthenticator
import app.chamich.library.authentication.IUser
import app.chamich.library.authentication.exceptions.AuthenticatorException
import app.chamich.library.logger.ILogger
import kotlinx.coroutines.launch

internal class SignUpViewModel @ViewModelInject constructor(
    private val authenticator: IAuthenticator,
    private val logger: ILogger
) : ViewModel() {

    private var user = MutableLiveData<Resource<IUser>>()

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Public Functions

    fun signUp(email: String, password: String) {
        logger.debug(message = "Sign Up (email: ${email}, password: ${password})")
        viewModelScope.launch {
            user.postValue(Resource.loading(null))
            try {
                user.postValue(Resource.success(authenticator.signUp(email, password)))
            } catch (exception: AuthenticatorException.SignUpException) {
                logger.error(message = "Exception while Sign Up", throwable = exception)
                user.postValue(Resource.error(exception))
            }
        }
    }

    fun getSignedUpUser(): LiveData<Resource<IUser>> = user

    fun resetSignedUpUser() {
        user = MutableLiveData()
    }

    //endregion
}
