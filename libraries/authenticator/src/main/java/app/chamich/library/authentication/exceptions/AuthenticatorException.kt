package app.chamich.library.authentication.exceptions


sealed class AuthenticatorException(
    exception: Exception
) : Throwable() {

    class SignInException(exception: Exception) : AuthenticatorException(exception)
    class SignUpException(exception: Exception) : AuthenticatorException(exception)
    class PasswordResetException(exception: Exception) : AuthenticatorException(exception)
}
