package uz.gita.banking.presentation.screen.Main.pages


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.orbitmvi.orbit.compose.collectAsState
import uz.gita.banking.presentation.screen.Home.vm.HomeIntent
import uz.gita.banking.presentation.screen.Home.vm.HomeUIState
import uz.gita.banking.presentation.screen.Home.vm.HomeViewModel
import uz.gita.bookappShoxruh.screens.Main.vm.impl.HomeViewModelImpl
import uz.gita.bookappShoxruh.data.BookData
import uz.gita.bookappShoxruh.screens.Main.extensions.BookItem

class Home() : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Home"
            val icon = rememberVectorPainter(Icons.Default.Home)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val viewModel: HomeViewModel = getViewModel<HomeViewModelImpl>()
        val uiState = viewModel.collectAsState().value
        HomePageComponent(uiState, viewModel::onEventDispatcher)
    }

    @Composable
    fun HomePageComponent(uiState: HomeUIState, onEventDispatcher: (HomeIntent) -> Unit) {
        var booksList: List<BookData> by remember {
            mutableStateOf(emptyList())
        }
        when (uiState) {
            is HomeUIState.Success -> {
                booksList = uiState.bookData
            }
            is HomeUIState.NoConnection -> {

            }
            is HomeUIState.Loading -> {

            }
            else -> {}
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(items = booksList) {
                    BookItem(bookData = it) {
                        onEventDispatcher(HomeIntent.ClickedBook(it))
                    }
                }
            }
        }
    }

}