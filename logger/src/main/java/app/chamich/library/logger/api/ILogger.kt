package app.chamich.library.logger.api


interface ILogger {

    fun error(tag: String?, message: String?, throwable: Throwable?)

    fun info(tag: String?, message: String)

    fun debug(tag: String?, message: String)

    fun warn(tag: String?, message: String)

    fun verbose(tag: String?, message: String)
}
