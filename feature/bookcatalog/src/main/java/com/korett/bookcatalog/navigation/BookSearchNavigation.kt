package com.korett.bookcatalog.navigation

import androidx.navigation.NavDirections

interface BookSearchNavigation {

    fun directionToBookDescription(bookId: Int): NavDirections

}