package app.chamich.feature.authentication.ui.password.forgot

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.chamich.feature.authentication.model.Resource
import app.chamich.library.authentication.IAuthenticator
import app.chamich.library.authentication.exceptions.AuthenticatorException
import app.chamich.library.logger.ILogger
import kotlinx.coroutines.launch

internal class ForgotPasswordViewModel @ViewModelInject constructor(
    private val authenticator: IAuthenticator,
    private val logger: ILogger
) : ViewModel() {

    private var resetPassword = MutableLiveData<Resource<Unit>>()

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Public Functions

    fun resetPassword(email: String) {
        logger.debug(message = "Reset Password (email: ${email})")
        viewModelScope.launch {
            resetPassword.postValue(Resource.loading(null))
            try {
                authenticator.resetPassword(email)
                resetPassword.postValue(Resource.success(null))
            } catch (exception: AuthenticatorException.PasswordResetException) {
                logger.error(message = "Exception while Password Reset", throwable = exception)
                resetPassword.postValue(Resource.error(exception))
            }
        }
    }

    fun getResetPassword(): LiveData<Resource<Unit>> = resetPassword

    fun resetResetPassword() {
        resetPassword = MutableLiveData()
    }

    //endregion
}
