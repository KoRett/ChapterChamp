package com.korett.bookcatalog.navigation

import androidx.navigation.NavDirections

interface FavouriteNavigation {

    fun directionToBookDescription(bookId: Int): NavDirections

}