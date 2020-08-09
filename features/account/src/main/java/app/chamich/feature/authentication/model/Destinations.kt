package app.chamich.feature.authentication.model

import androidx.annotation.IdRes
import app.chamich.feature.authentication.R

/**
 * Enumeration keeps navigation graph destination ids
 */
internal enum class Destinations(@IdRes val direction: Int) {
    SignIn(R.id.destination_fragment_sign_in),
    SignUp(R.id.destination_fragment_sign_up),
    ForgotPassword(R.id.destination_forgot_password)
}
