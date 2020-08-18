/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.library.authentication

data class FirebaseUser(
    override val uid: String,
    override val email: String,
    override val phone: String?,
    override val name: String?
) : IUser