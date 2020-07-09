package app.chamich.library.logger

import android.util.Log
import app.chamich.library.logger.api.ILogger


class Logger(
    private val commonTag: String = "PERFECTUS/"
): ILogger {

    init {
        Log.d("--| DEBUGGING |--",
            "Logger object created with ID: ${System.identityHashCode(this)}")
    }

    override fun error(tag: String?, message: String?, throwable: Throwable?) {
        Log.e(if(!tag.isNullOrBlank()) tag else commonTag, message, throwable)
    }

    override fun info(tag: String?, message: String) {
        Log.i(if(!tag.isNullOrBlank()) tag else commonTag, message)
    }

    override fun debug(tag: String?, message: String) {
        Log.d(if(!tag.isNullOrBlank()) tag else commonTag, message)
    }

    override fun warn(tag: String?, message: String) {
        Log.w(if(!tag.isNullOrBlank()) tag else commonTag, message)
    }

    override fun verbose(tag: String?, message: String) {
        Log.v(if(!tag.isNullOrBlank()) tag else commonTag, message)
    }
}
