package com.korett.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.korett.domain.repository.SettingsRepository
import com.korett.model.ThemeModel
import com.korett.ui.extension.MutableScreenStateFlow
import com.korett.ui.extension.ScreenStateFlow
import com.korett.ui.utils.LceState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _screenState: MutableScreenStateFlow<ThemeModel> =
        MutableStateFlow(LceState.Initial)
    val screenState: ScreenStateFlow<ThemeModel> get() = _screenState.asStateFlow()

    init {
        getTheme()
    }

    fun saveTheme(theme: ThemeModel) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                settingsRepository.saveTheme(theme)
            }.fold(
                onSuccess = { _screenState.value = LceState.Content(theme) },
                onFailure = { _screenState.value = LceState.Error(it) }
            )
        }
    }

    fun getTheme() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                settingsRepository.getTheme()
            }.fold(
                onSuccess = { _screenState.value = LceState.Content(it) },
                onFailure = { _screenState.value = LceState.Error(it) }
            )
        }
    }

    class Factory @Inject constructor(
        private val settingsRepository: SettingsRepository
    ) :
        ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SettingsViewModel(settingsRepository) as T
        }
    }
}