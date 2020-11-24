/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.library.cloud.api

interface Profile {
    val id: String
    val email: String
    val name: String?
    val photo: String?
}
