package app.chamich.feature.authentication.ui.signup

import app.chamich.library.authentication.IUser

interface SignUpListener {
    fun onSignUpCompleted(user: IUser)
}
