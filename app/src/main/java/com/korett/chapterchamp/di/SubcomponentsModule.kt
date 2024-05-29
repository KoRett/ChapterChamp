package com.korett.chapterchamp.di

import com.korett.bookcatalog.di.BookCatalogSubcomponent
import com.korett.bookcatalog.di.SettingsSubcomponent
import com.korett.bookdescription.di.BookDescriptionSubcomponent
import com.korett.favourite.di.FavouriteSubcomponent
import dagger.Module


@Module(
    subcomponents = [
        BookCatalogSubcomponent::class,
        BookDescriptionSubcomponent::class,
        FavouriteSubcomponent::class,
        SettingsSubcomponent::class
    ]
)
class SubcomponentsModule