package com.korett.data.di

import android.content.Context
import androidx.room.Room
import com.korett.data.database.AppDatabase
import com.korett.data.database.dao.BookDao
import com.korett.data.repository.BookRepositoryImpl
import com.korett.data.localsource.LocalSource
import com.korett.data.localsource.SharedPreferencesLocalSource
import com.korett.data.repository.SettingsRepositoryImpl
import com.korett.domain.repository.BookRepository
import com.korett.domain.repository.SettingsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
interface DataBindModule {

    @Binds
    fun bindBookRepositoryImpl_to_BookRepository(bookRepositoryImpl: BookRepositoryImpl): BookRepository

    @Binds
    fun bindSettingsRepostitoryImpl_to_SettingsRepostitory(settingsRepositoryImpl: SettingsRepositoryImpl): SettingsRepository

    @Binds
    fun bindSharedPreferencesLocalSource_to_LocalSource(sharedPreferencesLocalSource: SharedPreferencesLocalSource): LocalSource

    companion object {
        @Provides
        @Singleton
        fun provideAppDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                name = "app_database.db"
            ).build()
        }

        @Provides
        fun provideBookDao(appDatabase: AppDatabase): BookDao = appDatabase.bookDao()
    }
}