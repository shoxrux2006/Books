package uz.gita.banking.presentation.screen.Splash.vm.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.banking.presentation.screen.Main.MainScreen
import uz.gita.bookappShoxruh.BuildConfig
import uz.gita.bookappShoxruh.screens.Splash.vm.SplashUiState
import uz.gita.bookappShoxruh.screens.Splash.vm.SplashVM
import uz.gita.bookappShoxruh.navigation.AppNavigation
import javax.inject.Inject

@HiltViewModel
class SplashVMImpl @Inject constructor(
    val navigator: AppNavigation,

    ) : SplashVM, ViewModel() {
    init {
        viewModelScope.launch {
            if (!BuildConfig.DEBUG) {
                delay(2000)
            }
            navigator.replaceWith(MainScreen())
        }
    }

    override fun onEventDispatcher(intent: Nothing) = intent {


    }

    override val container: Container<SplashUiState, Nothing> = container(
        SplashUiState(
            BuildConfig.VERSION_NAME
        )
    )


}