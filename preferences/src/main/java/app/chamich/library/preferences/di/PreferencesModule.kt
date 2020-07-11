package app.chamich.library.preferences.di

import app.chamich.library.preferences.IPreferences
import app.chamich.library.preferences.internal.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object PreferencesModule {

    @Provides
    @Singleton
    fun providesPreferences(): IPreferences = Preferences()
}