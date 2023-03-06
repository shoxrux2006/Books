package uz.gita.bookappShoxruh.screens.Main.vm

import cafe.adriel.voyager.navigator.tab.Tab
import uz.gita.bookappShoxruh.utils.AppViewModel

interface MainViewModel : AppViewModel<MainIntent, MainUIState, Nothing> {

}

sealed interface MainIntent {
    data class Navigation(val currentTab: Tab) : MainIntent
}

sealed interface MainUIState {
    data class Default(val currentTab: Tab) : MainUIState
}

