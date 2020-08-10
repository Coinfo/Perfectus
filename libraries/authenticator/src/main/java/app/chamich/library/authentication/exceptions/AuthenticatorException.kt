/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.library.authentication.exceptions


sealed class AuthenticatorException(
    override val message: String?, override val cause: Throwable?, localizedMessage: String?
) : Exception() {

    class SignInException(
        exception: Exception
    ) : AuthenticatorException(
        exception.message,
        exception.cause,
        exception.cause?.localizedMessage
    )

    class SignUpException(
        exception: Exception
    ) : AuthenticatorException(
        exception.message,
        exception.cause,
        exception.cause?.localizedMessage
    )

    class PasswordResetException(
        exception: Exception
    ) : AuthenticatorException(
        exception.message,
        exception.cause,
        exception.cause?.localizedMessage
    )
}
