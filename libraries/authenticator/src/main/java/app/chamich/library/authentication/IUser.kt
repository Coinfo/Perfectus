/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.library.authentication

/**
 * An interface which provides authenticated user data
 */
interface IUser {
    val uid: String
    val email: String
    val phone: String?
    val name: String?
}
