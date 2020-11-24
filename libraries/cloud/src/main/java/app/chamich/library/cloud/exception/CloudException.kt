/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.library.cloud.exception

open class CloudException(
    override val message: String?, private val throwable: Throwable
) : Exception(message, throwable)

