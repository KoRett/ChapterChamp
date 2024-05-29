package com.korett.ui.extension

import com.korett.ui.utils.LceState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

typealias MutableScreenStateFlow<T> = MutableStateFlow<LceState<T>>
typealias ScreenStateFlow<T> = StateFlow<LceState<T>>