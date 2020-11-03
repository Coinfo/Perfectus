/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.library.logger


interface ILogger {

    fun error(tag: String? = null, message: String?, throwable: Throwable?)

    fun error(tag: String? = null, message: String)

    fun info(tag: String? = null, message: String)

    fun debug(tag: String? = null, message: String)

    fun warn(tag: String? = null, message: String)

    fun verbose(tag: String? = null, message: String)
}
