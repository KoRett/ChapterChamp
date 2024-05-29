package com.korett.chapterchamp.di

import androidx.navigation.NavDirections
import com.korett.bookcatalog.catalog.BookCatalogFragmentDirections
import com.korett.bookcatalog.navigation.BookCatalogNavigation
import com.korett.bookcatalog.navigation.BookSearchNavigation
import com.korett.bookcatalog.navigation.FavouriteNavigation
import com.korett.bookcatalog.search.SearchFragmentDirections
import com.korett.favourite.FavouriteFragmentDirections
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class NavigationModule {

    @Provides
    @Singleton
    fun provideBookCatalogNavigation() = object : BookCatalogNavigation {

        override fun directionToSearch(): NavDirections =
            BookCatalogFragmentDirections.actionBookCatalogFragmentToSearchFragment()

        override fun directionToBookDescription(bookId: Int): NavDirections =
            BookCatalogFragmentDirections.actionBookCatalogFragmentToBookDescriptionNavGraph(bookId)
    }

    @Provides
    @Singleton
    fun provideBookSearchNavigation() = object : BookSearchNavigation {
        override fun directionToBookDescription(bookId: Int): NavDirections =
            SearchFragmentDirections.actionSearchFragmentToBookDescriptionNavGraph(bookId)
    }

    @Provides
    @Singleton
    fun provideFavouriteNavigation() = object : FavouriteNavigation {
        override fun directionToBookDescription(bookId: Int): NavDirections =
            FavouriteFragmentDirections.actionFavouriteFragmentToBookDescriptionNavGraph(bookId)
    }

}