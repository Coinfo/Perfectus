/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.library.logger

import android.util.Log


class Logger: ILogger {

    init {
        Log.d(TAG_DEBUG, "Logger object created with ID: ${System.identityHashCode(this)}")
    }

    override fun error(tag: String?, message: String?, throwable: Throwable?) {
        Log.e(if(!tag.isNullOrBlank()) tag else TAG_ERROR, message, throwable)
    }

    override fun info(tag: String?, message: String) {
        Log.i(if(!tag.isNullOrBlank()) tag else TAG_INFO, message)
    }

    override fun debug(tag: String?, message: String) {
        Log.d(if(!tag.isNullOrBlank()) tag else TAG_DEBUG, message)
    }

    override fun warn(tag: String?, message: String) {
        Log.w(if(!tag.isNullOrBlank()) tag else TAG_WARNING, message)
    }

    override fun verbose(tag: String?, message: String) {
        Log.v(if(!tag.isNullOrBlank()) tag else TAG_VERBOSE, message)
    }

    companion object {
        private const val TAG_DEBUG = "--| DEBUG |--"
        private const val TAG_ERROR = "--| ERROR |--"
        private const val TAG_INFO  = "--| INFO |--"
        private const val TAG_WARNING = "--| WARNING |--"
        private const val TAG_VERBOSE = "--| VERBOSE |--"
    }
}
