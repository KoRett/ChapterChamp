package com.korett.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.korett.data.database.dao.BookDao
import com.korett.data.database.entity.BookEntity

@Database(
    entities = [
        BookEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
}