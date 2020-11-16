/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.goals.di

import android.content.Context
import androidx.room.Room
import app.chamich.feature.goals.repository.GoalsRepository
import app.chamich.feature.goals.repository.api.IRepository
import app.chamich.library.database.IDatabase
import app.chamich.library.logger.ILogger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
internal class GoalsModule {

    @Provides
    @Singleton
    fun providesRepository(
        @ApplicationContext context: Context,
        logger: ILogger
    ): IRepository {
        val db = Room.databaseBuilder(context, IDatabase::class.java, GOALS_DATABASE_NAME)
            .fallbackToDestructiveMigration().build()
        return GoalsRepository(db.getGoalsDatabase(), db.getGoalsProgressDatabase(), logger)
    }


    private companion object {
        const val GOALS_DATABASE_NAME = "goals.db"
    }
}
