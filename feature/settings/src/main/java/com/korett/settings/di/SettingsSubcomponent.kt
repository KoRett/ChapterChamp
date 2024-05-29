package com.korett.bookcatalog.di

import com.korett.settings.SettingsFragment
import dagger.Subcomponent

@Subcomponent
interface SettingsSubcomponent {
    fun inject(settingsFragment: SettingsFragment)

    @Subcomponent.Builder
    interface Builder {
        fun build(): SettingsSubcomponent
    }

}