/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.library.authentication

import android.net.Uri

data class FirebaseUser(
    override val uid: String?,
    override val email: String?,
    override val phone: String?,
    override val displayName: String?,
    override val photoUrl: Uri?
) : IUser
