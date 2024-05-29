package com.korett.bookcatalog.navigation

import androidx.navigation.NavDirections

interface BookCatalogNavigation {

    fun directionToSearch(): NavDirections

    fun directionToBookDescription(bookId: Int): NavDirections

}