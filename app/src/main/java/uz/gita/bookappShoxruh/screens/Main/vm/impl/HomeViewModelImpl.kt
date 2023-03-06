package uz.gita.bookappShoxruh.screens.Main.vm.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.banking.presentation.screen.Home.vm.*
import uz.gita.banking.utils.NetworkResponse
import uz.gita.bookappShoxruh.data.toBookEntity
import uz.gita.bookappShoxruh.domain.BooksRepository
import uz.gita.bookappShoxruh.navigation.AppNavigation
import uz.gita.bookappShoxruh.screens.Details.BookDetailScreen
import javax.inject.Inject

@HiltViewModel
class HomeViewModelImpl @Inject constructor(
    val repository: BooksRepository,
    val navigation: AppNavigation
) : HomeViewModel, ViewModel() {


    override fun onEventDispatcher(intent: HomeIntent) = intent {
        when (intent) {
            is HomeIntent.ClickedBook -> {
                navigation.navigateTo(BookDetailScreen(intent.bookData))
            }
        }
    }


    private fun getBooks() = intent {
        viewModelScope.launch {
            repository.getAllBooks().collect {
                when (it) {
                    is NetworkResponse.Error -> {
                        postSideEffect(HomeSideEffect.Message(it.message))
                    }
                    is NetworkResponse.Failure -> {
                        postSideEffect(HomeSideEffect.Message("nimadir noto'g'ri bajarildi"))
                    }
                    is NetworkResponse.Loading -> {
                        reduce { HomeUIState.Loading(it.isLoading) }
                    }
                    is NetworkResponse.NoConnection -> {
                        reduce { HomeUIState.NoConnection }
                    }
                    is NetworkResponse.Success -> {

                        it.data?.let { its ->
                            val bookEntityList = its.map { it.toBookEntity() }
                            repository.insertBooks(bookEntityList)
                            reduce { HomeUIState.Success(its) }
                        }
                    }
                }
            }
        }
    }


    override val container: Container<HomeUIState, HomeSideEffect> =
        container(HomeUIState.Loading(isLoad = true))


    init {
        getBooks()
    }

}