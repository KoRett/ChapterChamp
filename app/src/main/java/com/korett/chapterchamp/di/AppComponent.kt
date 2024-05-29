package com.korett.chapterchamp.di

import android.content.Context
import com.korett.bookcatalog.di.BookCatalogSubcomponent
import com.korett.bookcatalog.di.SettingsSubcomponent
import com.korett.bookdescription.di.BookDescriptionSubcomponent
import com.korett.chapterchamp.app.App
import com.korett.data.di.DataBindModule
import com.korett.favourite.di.FavouriteSubcomponent
import com.korett.network.di.NetworkModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        SubcomponentsModule::class,
        NavigationModule::class,
        NetworkModule::class,
        DataBindModule::class
    ]
)
@Singleton
interface AppComponent {

    fun bookCatalogSubcomponent(): BookCatalogSubcomponent.Builder
    fun bookDescriptionSubcomponent(): BookDescriptionSubcomponent.Builder
    fun favouriteSubcomponent(): FavouriteSubcomponent.Builder
    fun settingsSubcomponent(): SettingsSubcomponent.Builder
    fun inject(app: App)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder
        fun build(): AppComponent
    }
}