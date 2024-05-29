package com.korett.favourite.di

import com.korett.favourite.FavouriteFragment
import dagger.Subcomponent

@Subcomponent
interface FavouriteSubcomponent {

    fun inject(favouriteFragment: FavouriteFragment)

    @Subcomponent.Builder
    interface Builder {
        fun build(): FavouriteSubcomponent
    }

}