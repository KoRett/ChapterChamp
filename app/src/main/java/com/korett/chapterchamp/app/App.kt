package com.korett.chapterchamp.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.korett.bookcatalog.di.BookCatalogSubcomponent
import com.korett.bookcatalog.di.BookCatalogSubcomponentProvider
import com.korett.bookcatalog.di.SettingsSubcomponent
import com.korett.bookcatalog.di.SettingsSubcomponentProvider
import com.korett.bookdescription.di.BookDescriptionSubcomponent
import com.korett.bookdescription.di.BookDescriptionSubcomponentProvider
import com.korett.chapterchamp.di.AppComponent
import com.korett.chapterchamp.di.DaggerAppComponent
import com.korett.domain.repository.SettingsRepository
import com.korett.favourite.di.FavouriteSubcomponent
import com.korett.favourite.di.FavouriteSubcomponentProvider
import com.korett.model.ThemeModel
import javax.inject.Inject

class App : Application(), BookCatalogSubcomponentProvider, BookDescriptionSubcomponentProvider,
    FavouriteSubcomponentProvider, SettingsSubcomponentProvider {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder().context(this).build()
    }

    private val bookCatalogSubcomponent: BookCatalogSubcomponent =
        appComponent.bookCatalogSubcomponent().build()

    private val bookDescriptionSubcomponent: BookDescriptionSubcomponent =
        appComponent.bookDescriptionSubcomponent().build()

    private val favouriteSubcomponent: FavouriteSubcomponent =
        appComponent.favouriteSubcomponent().build()

    override fun provideSettingsSubcomponent(): SettingsSubcomponent =
        appComponent.settingsSubcomponent().build()

    override fun provideBookCatalogSubcomponent(): BookCatalogSubcomponent = bookCatalogSubcomponent

    override fun provideBookDescriptionSubcomponentProvider(): BookDescriptionSubcomponent =
        bookDescriptionSubcomponent

    override fun provideFavouriteSubcomponent(): FavouriteSubcomponent = favouriteSubcomponent

    @Inject
    lateinit var settingsRepository: SettingsRepository

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)

        setTheme(settingsRepository.getTheme())
    }

    private fun setTheme(themeModel: ThemeModel) {
        val theme = when (themeModel) {
            ThemeModel.Dark -> AppCompatDelegate.MODE_NIGHT_YES
            ThemeModel.Light -> AppCompatDelegate.MODE_NIGHT_NO
            ThemeModel.System -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }

        AppCompatDelegate.setDefaultNightMode(theme)
    }

}