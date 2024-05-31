package com.korett.network.di

import com.korett.network.retrofit.ChapterChampSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideChapterChampStorage() = ChapterChampSource.create()

}