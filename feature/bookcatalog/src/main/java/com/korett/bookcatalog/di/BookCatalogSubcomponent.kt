package com.korett.bookcatalog.di

import com.korett.bookcatalog.catalog.BookCatalogFragment
import com.korett.bookcatalog.search.SearchFragment
import dagger.Subcomponent
import javax.inject.Singleton

@Subcomponent
interface BookCatalogSubcomponent {
    fun inject(bookCatalogFragment: BookCatalogFragment)
    fun inject(bookCatalogFragment: SearchFragment)

    @Subcomponent.Builder
    interface Builder {
        fun build(): BookCatalogSubcomponent
    }

}