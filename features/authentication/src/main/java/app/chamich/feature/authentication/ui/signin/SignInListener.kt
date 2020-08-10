/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.authentication.ui.signin

import app.chamich.library.authentication.IUser

interface SignInListener {
    fun onSignInCompleted(user: IUser)
}
