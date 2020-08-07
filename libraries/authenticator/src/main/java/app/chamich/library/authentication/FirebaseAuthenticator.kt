package app.chamich.library.authentication


import app.chamich.library.authentication.exceptions.AuthenticatorException
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

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
            throw AuthenticatorException.SignInException(exception)
        }
    }

    @Throws(AuthenticatorException.PasswordResetException::class)
    override suspend fun resetPassword(email: String) {
        try {
            authenticator.sendPasswordResetEmail(email).await()
        } catch (exception: FirebaseException) {
            throw AuthenticatorException.PasswordResetException(exception)
        } catch (exception: IllegalArgumentException) {
            throw AuthenticatorException.SignInException(exception)
        }
    }

    override fun signOut() {
        authenticator.signOut()
    }

    override fun isSignedIn() = authenticator.currentUser != null
}
