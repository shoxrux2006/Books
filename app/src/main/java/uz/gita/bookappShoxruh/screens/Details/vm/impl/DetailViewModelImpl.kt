package uz.gita.bookappShoxruh.screens.Details.vm.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.banking.utils.NetworkResponse
import uz.gita.banking.utils.l
import uz.gita.bookappShoxruh.data.BookData
import uz.gita.bookappShoxruh.data.UploadData
import uz.gita.bookappShoxruh.data.room.BookEntity
import uz.gita.bookappShoxruh.domain.BooksRepository
import uz.gita.bookappShoxruh.navigation.AppNavigation
import uz.gita.bookappShoxruh.screens.Details.vm.DetailIntent
import uz.gita.bookappShoxruh.screens.Details.vm.DetailSideEffect
import uz.gita.bookappShoxruh.screens.Details.vm.DetailUIState
import uz.gita.bookappShoxruh.screens.Details.vm.DetialViewModel
import uz.gita.bookappShoxruh.screens.PDFScreen.PdfViewerScreen
import javax.inject.Inject

@HiltViewModel
class DetailViewModelImpl @Inject constructor(
    val repository: BooksRepository,
    val navigation: AppNavigation
) : DetialViewModel, ViewModel() {
    private var bookEntity: BookEntity? = null
    private var bookData: BookData? = null
    override fun getBook(bookData: BookData) = intent {
        viewModelScope.launch {
            this@DetailViewModelImpl.bookData = bookData
            repository.getBook(bookData.documentName).collect {
                l("update")
                bookEntity = it
                reduce { DetailUIState.GetOffline(it) }
            }
        }
    }

    override fun onEventDispatcher(intent: DetailIntent) = intent {
        when (intent) {
            DetailIntent.Dislike -> {
                bookEntity?.let {
                    if (it.isLike != -1) {
                        dislike()
                    }
                }
            }
            is DetailIntent.Download -> {
                bookData?.let { download(it) }
            }
            is DetailIntent.Like -> {
                bookEntity?.let {
                    if (it.isLike != 1) {
                        bookData?.let {
                            like(it)
                        }
                    }
                }
            }
            is DetailIntent.Open -> {
                bookEntity?.let {
                    navigation.navigateTo(PdfViewerScreen(it.filePath))
                }
            }
            DetailIntent.Cancel -> {
                cancel()
                reduce { DetailUIState.Cancel }
            }
            DetailIntent.Pause -> {
                pause()
            }
            DetailIntent.Resume -> {
                resume()
            }
        }
    }


    private fun download(bookData: BookData) = intent {
        repository.downloadBook(bookData.downloadLink).collect {
            when (it) {
                UploadData.Cancel -> {
                    reduce { DetailUIState.Cancel }
                }
                is UploadData.Complete -> {
                    bookEntity?.let { it1 ->
                        it1.filePath = it.bookNameInStorage
                        repository.updateBook(it1)
                    }
                }
                UploadData.Failture -> {
                    reduce { DetailUIState.Failture }
                }
                UploadData.NoConnection -> {
                    reduce { DetailUIState.NoConnection }
                }
                UploadData.Pause -> {
                    reduce { DetailUIState.Pause }
                }
                is UploadData.Progress -> {
                    reduce { DetailUIState.Progress(it.progress) }
                }
            }
        }
    }


    private fun dislike() = intent {
        viewModelScope.launch {
            bookData?.let {
                repository.dislike(it).collect { its ->
                    when (its) {
                        is NetworkResponse.Error -> {
                            postSideEffect(DetailSideEffect.Message(its.message))
                        }
                        is NetworkResponse.Failure -> {
                            postSideEffect(DetailSideEffect.Message("nimadir xato bo'ldi"))
                        }
                        is NetworkResponse.Loading -> {
                            reduce { DetailUIState.Loading(isLoad = its.isLoading) }
                        }
                        is NetworkResponse.NoConnection -> {
                            reduce { DetailUIState.NoConnection }
                        }
                        is NetworkResponse.Success -> {
                            bookEntity?.let { it1 ->
                                it1.isLike = -1
                                this@DetailViewModelImpl.bookData?.let {
                                    it.like = its.data!!
                                    reduce { DetailUIState.OnlineBookdata(it) }
                                    repository.updateBook(it1)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun resume() {
        repository.resumeDownload()
    }

    private fun pause() {
        repository.pauseDownload()
    }

    private fun cancel() {
        repository.cancelDownload()
    }


    private fun like(bookData: BookData) = intent {
        viewModelScope.launch {
            repository.like(bookData).collect { its ->
                when (its) {
                    is NetworkResponse.Error -> {
                        postSideEffect(DetailSideEffect.Message(its.message))
                    }
                    is NetworkResponse.Failure -> {
                        postSideEffect(DetailSideEffect.Message("nimadir xato bo'ldi"))
                    }
                    is NetworkResponse.Loading -> {
                        reduce { DetailUIState.Loading(isLoad = its.isLoading) }
                    }
                    is NetworkResponse.NoConnection -> {
                        reduce { DetailUIState.NoConnection }
                    }
                    is NetworkResponse.Success -> {
                        bookEntity?.let { it1 ->
                            it1.isLike = 1
                            this@DetailViewModelImpl.bookData?.let {
                                it.like = its.data!!
                                reduce { DetailUIState.OnlineBookdata(it) }
                                repository.updateBook(it1)
                            }
                        }
                    }
                }
            }
        }
    }

    override val container: Container<DetailUIState, DetailSideEffect> =
        container(DetailUIState.Loading(isLoad = true))


}