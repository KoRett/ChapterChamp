package com.korett.settings

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.korett.bookcatalog.di.SettingsSubcomponentProvider
import com.korett.model.ThemeModel
import com.korett.settings.databinding.FragmentSettingsBinding
import com.korett.ui.utils.LceState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Provider

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    @Inject
    lateinit var viewModelFactory: Provider<SettingsViewModel.Factory>
    private val viewModel: SettingsViewModel by viewModels { viewModelFactory.get() }

    private val binding: FragmentSettingsBinding by viewBinding()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val settingsSubcomponent =
            (requireActivity().application as SettingsSubcomponentProvider).provideSettingsSubcomponent()
        settingsSubcomponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.toggleButtonTheme.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.bt_dark_theme -> setTheme(ThemeModel.Dark)
                    R.id.bt_system_theme -> setTheme(ThemeModel.System)
                    R.id.bt_light_theme -> setTheme(ThemeModel.Light)
                }
            }
        }

        viewModel.screenState
            .flowWithLifecycle(lifecycle)
            .onEach(::render)
            .launchIn(lifecycleScope)
    }

    private fun render(state: LceState<ThemeModel>) {
        when (state) {
            is LceState.Error -> {
                Log.d(this::class.simpleName, state.error.stackTraceToString())
            }
            LceState.Initial -> {
                binding.toggleButtonTheme.check(getThemeButtonId(ThemeModel.System))
            }

            LceState.Loading -> Unit
            is LceState.Content -> {
                binding.toggleButtonTheme.check(getThemeButtonId(state.data))
            }
        }
    }

    private fun getThemeButtonId(themeModel: ThemeModel): Int {
        return when (themeModel) {
            ThemeModel.Dark -> R.id.bt_dark_theme
            ThemeModel.System -> R.id.bt_system_theme
            ThemeModel.Light -> R.id.bt_light_theme
        }
    }

    private fun setTheme(themeModel: ThemeModel) {
        val theme = when (themeModel) {
            ThemeModel.Dark -> AppCompatDelegate.MODE_NIGHT_YES
            ThemeModel.Light -> AppCompatDelegate.MODE_NIGHT_NO
            ThemeModel.System -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }

        AppCompatDelegate.setDefaultNightMode(theme)
        viewModel.saveTheme(themeModel)
    }
}