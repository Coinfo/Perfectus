package app.chamich.library.logger.di

import app.chamich.library.logger.internal.Logger
import app.chamich.library.logger.ILogger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object LoggerModule {

    @Provides
    @Singleton
    fun providesLogger(): ILogger = Logger()
}