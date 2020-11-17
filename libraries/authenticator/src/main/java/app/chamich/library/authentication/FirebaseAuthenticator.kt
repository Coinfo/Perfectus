/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.library.authentication


import android.content.Context
import android.content.Intent
import app.chamich.library.authentication.exceptions.AuthenticatorException
import app.chamich.library.authenticator.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

class FirebaseAuthenticator(
    private val authenticator: FirebaseAuth = FirebaseAuth.getInstance(),
    private val context: Context
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

    override suspend fun handleGoogleSignIn(data: Intent?): IUser {
        val googleSignInResult = GoogleSignIn.getSignedInAccountFromIntent(data).await()
        val credential = GoogleAuthProvider.getCredential(googleSignInResult.idToken, null)
        val authenticationResult = authenticator.signInWithCredential(credential).await()
        val user = authenticationResult.user

        return FirebaseUser(user!!.uid, user.email!!, user.phoneNumber, user.displayName)
    }

    override fun signOut() {
        authenticator.signOut()
    }

    override fun isSignedIn() = authenticator.currentUser != null

    override fun getCurrentUser(): IUser? {
        authenticator.currentUser?.let {
            return FirebaseUser(it.uid, it.email!!, it.phoneNumber, it.displayName)
        }
        return null
    }

    override fun getGoogleSignInIntent(context: Context) =
        GoogleSignIn.getClient(
            context, GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        ).signInIntent

}
