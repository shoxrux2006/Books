package uz.gita.banking.presentation.screen.Home.vm

import uz.gita.bookappShoxruh.data.BookData
import uz.gita.bookappShoxruh.utils.AppViewModel


interface HomeViewModel : AppViewModel<HomeIntent, HomeUIState, HomeSideEffect> {
}

sealed interface HomeIntent {
    data class ClickedBook(val bookData: BookData) : HomeIntent
}

sealed interface HomeUIState {
    object NoConnection : HomeUIState
    data class Success(val bookData: List<BookData>) : HomeUIState
    data class Loading(val isLoad: Boolean) : HomeUIState
}

sealed interface HomeSideEffect {
    data class Message(val text: String) : HomeSideEffect
}