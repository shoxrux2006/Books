package uz.gita.bookappShoxruh.screens.Details.vm

import uz.gita.bookappShoxruh.data.BookData
import uz.gita.bookappShoxruh.data.UploadData
import uz.gita.bookappShoxruh.data.room.BookEntity
import uz.gita.bookappShoxruh.utils.AppViewModel


interface DetialViewModel : AppViewModel<DetailIntent, DetailUIState, DetailSideEffect> {
    fun getBook(bookData: BookData)
}

sealed interface DetailIntent {
    object Resume : DetailIntent
    object Pause : DetailIntent
    object Like : DetailIntent
    object Dislike : DetailIntent
    object Download : DetailIntent
    object Cancel : DetailIntent
    object Open : DetailIntent
}

sealed interface DetailUIState {
    object NoConnection : DetailUIState
    data class GetOffline(val bookEntity: BookEntity) : DetailUIState
    data class OnlineBookdata(val bookData: BookData) : DetailUIState
    object Failture : DetailUIState
    object Cancel : DetailUIState
    object Pause : DetailUIState
    class Progress(val progress: Int) : DetailUIState
    data class Loading(val isLoad: Boolean) : DetailUIState
}

sealed interface DetailSideEffect {
    data class Message(val text: String) : DetailSideEffect
}