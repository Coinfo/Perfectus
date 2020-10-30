/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.library.authentication

import android.content.Context
import android.content.Intent
import app.chamich.library.authentication.exceptions.AuthenticatorException
import kotlin.Throws

/**
 * An interface which provides authentication functionality
 */
interface IAuthenticator {

    /**
     * Signs in existing account for given [email] and [password]
     *
     * @return [IUser] if sign in was successful
     * @throws [AuthenticatorException.SignInException]
     */
    @Throws(AuthenticatorException.SignInException::class)
    suspend fun signIn(email: String, password: String): IUser

    /**
     * Creates a new account for given [email] and [password]
     *
     * @return [IUser] if account was created successful
     * @throws [AuthenticatorException.SignUpException]
     */
    @Throws(AuthenticatorException.SignUpException::class)
    suspend fun signUp(email: String, password: String): IUser

    /**
     * Resets the user password for given [email] address
     *
     * @throws [AuthenticatorException.PasswordResetException]
     */
    @Throws(AuthenticatorException.PasswordResetException::class)
    suspend fun resetPassword(email: String)

    @Throws(AuthenticatorException.GoogleSignInException::class)
    suspend fun finalizeGoogleSignIn(data: Intent?): IUser

    fun createGoogleSignInIntent(context: Context): Intent

    /**
     * Signs out the user
     */
    fun signOut()

    /**
     * Checks if the user is signed in or not
     *
     * @return true if user is signed in otherwise false
     */
    fun isSignedIn(): Boolean
}
