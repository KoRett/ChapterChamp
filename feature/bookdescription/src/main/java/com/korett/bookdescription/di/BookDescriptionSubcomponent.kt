package com.korett.bookdescription.di

import com.korett.bookdescription.BookDescriptionFragment
import dagger.Subcomponent


@Subcomponent
interface BookDescriptionSubcomponent {
    fun inject(bookDescriptionFragment: BookDescriptionFragment)

    @Subcomponent.Builder
    interface Builder {
        fun build(): BookDescriptionSubcomponent
    }

}