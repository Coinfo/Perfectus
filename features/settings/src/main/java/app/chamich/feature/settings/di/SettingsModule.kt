/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.settings.di

import app.chamich.feature.settings.api.Settings
import app.chamich.library.logger.ILogger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class SettingsModule {

    @Provides
    @Singleton
    fun providesSettings(logger: ILogger) = Settings(logger)
}
