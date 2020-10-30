/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.library.authentication


import android.content.Context
import android.content.Intent
import app.chamich.library.authentication.exceptions.AuthenticatorException
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import kotlin.Throws

class FirebaseAuthenticator(
    private val authenticator: FirebaseAuth = FirebaseAuth.getInstance()
) : IAuthenticator {

    @Throws(AuthenticatorException.SignInException::class)
    override suspend fun signIn(email: String, password: String): IUser {
        try {
            val result = authenticator.signInWithEmailAndPassword(email, password).await()
            val user = result.user ?: throw AuthenticatorException.SignInException(
                IllegalStateException("Signed In User is null")
            )
            return FirebaseUser(user.uid, email, user.phoneNumber, user.displayName)
        } catch (exception: FirebaseException) {
            throw AuthenticatorException.SignInException(exception)
        } catch (exception: IllegalArgumentException) {
            throw AuthenticatorException.SignInException(exception)
        }
    }

    @Throws(AuthenticatorException.SignUpException::class)
    override suspend fun signUp(email: String, password: String): IUser {
        try {
            val result = authenticator.createUserWithEmailAndPassword(email, password).await()
            val user = result.user ?: throw AuthenticatorException.SignUpException(
                IllegalStateException("Signed Up User is null")
            )
            return FirebaseUser(user.uid, email, user.phoneNumber, user.displayName)
        } catch (exception: FirebaseException) {
            throw AuthenticatorException.SignUpException(exception)
        } catch (exception: IllegalArgumentException) {
            throw AuthenticatorException.SignUpException(exception)
        }
    }

    @Throws(AuthenticatorException.PasswordResetException::class)
    override suspend fun resetPassword(email: String) {
        try {
            authenticator.sendPasswordResetEmail(email).await()
        } catch (exception: FirebaseException) {
            throw AuthenticatorException.PasswordResetException(exception)
        } catch (exception: IllegalArgumentException) {
            throw AuthenticatorException.PasswordResetException(exception)
        }
    }

    override fun createGoogleSignInIntent(context: Context): Intent =
        GoogleSignIn.getClient(
            context, GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                //   .requestIdToken(WEB_CLIENT_ID)
                .requestEmail()
                .build()
        ).signInIntent

    @Throws(AuthenticatorException.GoogleSignInException::class)
    override suspend fun finalizeGoogleSignIn(data: Intent?): IUser {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)
            if (account != null) {
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                try {
                    val result = authenticator.signInWithCredential(credential).await()
                    val user = result.user ?: throw AuthenticatorException.GoogleSignInException(
                        IllegalStateException("Google Signed Up User is null")
                    )
                    return FirebaseUser(user.uid, user.email!!, user.phoneNumber, user.displayName)
                } catch (exception: IllegalArgumentException) {
                    throw AuthenticatorException.GoogleSignInException(exception)
                }
            } else {
                throw AuthenticatorException.GoogleSignInException(
                    IllegalStateException("Google Account is null")
                )
            }
        } catch (exception: ApiException) {
            throw AuthenticatorException.GoogleSignInException(exception)
        } catch (exception: IllegalArgumentException) {
            throw AuthenticatorException.GoogleSignInException(exception)
        }
    }

    override fun signOut() {
        authenticator.signOut()
    }

    override fun isSignedIn() = authenticator.currentUser != null

    companion object {
        private const val WEB_CLIENT_ID =
            "354909374087-hobh3odq8dbnoo3gu800se52oka8fp9g.apps.googleusercontent.com"
    }
}
