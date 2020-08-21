/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.perfectus.di

import android.content.Context
import app.chamich.library.authentication.FirebaseAuthenticator
import app.chamich.library.authentication.IAuthenticator
import app.chamich.library.logger.ILogger
import app.chamich.library.logger.Logger
import app.chamich.library.preferences.IPreferences
import app.chamich.library.preferences.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


/**
 * This module includes [ILogger] and [IPreferences] objects in to the [ApplicationComponent]
 * Objects provided by functions which are annotated with [Singleton] will be created only once.
 */
@Module
@InstallIn(ApplicationComponent::class)
object PerfectusModule {

    @Provides
    @Singleton
    fun providesLogger(): ILogger = Logger()

    @Provides
    @Singleton
    fun providesPreferences(
        @ApplicationContext context: Context
    ): IPreferences = Preferences(context)

    @Provides
    @Singleton
    fun providesAuthenticator(): IAuthenticator = FirebaseAuthenticator()
}
