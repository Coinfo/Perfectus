/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.library.authentication

import android.net.Uri

/**
 * An interface which provides authenticated user data
 */
interface IUser {
    val uid: String?
    val email: String?
    val phone: String?
    val displayName: String?
    val photoUrl: Uri?
}
