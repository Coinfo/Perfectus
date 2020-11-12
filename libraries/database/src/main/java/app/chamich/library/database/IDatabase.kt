/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.library.database

import androidx.room.Database
import androidx.room.RoomDatabase
import app.chamich.library.database.api.IGoalsDatabase
import app.chamich.library.database.api.IGoalsProgressDatabase
import app.chamich.library.database.entity.GoalEntity
import app.chamich.library.database.entity.GoalProgressEntity


@Database(entities = [GoalEntity::class, GoalProgressEntity::class], version = 1)
abstract class IDatabase : RoomDatabase() {

    abstract fun getGoalsDatabase(): IGoalsDatabase

    abstract fun getGoalsProgressDatabase(): IGoalsProgressDatabase
}
