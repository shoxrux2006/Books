package uz.gita.banking.presentation.screen.Main.vm.impl

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.navigator.tab.Tab
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.banking.presentation.screen.Main.pages.Home
import uz.gita.banking.utils.NetworkResponse
import uz.gita.banking.utils.l
import uz.gita.bookappShoxruh.domain.BooksRepository
import uz.gita.bookappShoxruh.screens.Main.vm.MainIntent
import uz.gita.bookappShoxruh.screens.Main.vm.MainUIState
import uz.gita.bookappShoxruh.screens.Main.vm.MainViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModelImpl @Inject constructor(
) : MainViewModel, ViewModel() {

    private var currentTab: Tab = Home()
    override fun onEventDispatcher(intent: MainIntent) = intent {
        when (intent) {
            is MainIntent.Navigation -> {
                currentTab = intent.currentTab
                reduce {
                    MainUIState.Default(currentTab)
                }

            }
        }
    }

    override val container: Container<MainUIState, Nothing> =
        container(MainUIState.Default(Home()))


}
