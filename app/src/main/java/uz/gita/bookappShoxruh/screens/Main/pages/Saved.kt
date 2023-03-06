package uz.gita.banking.presentation.screen.Main.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import uz.gita.bookappShoxruh.R

class Saved : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Saved"
            val icon = painterResource(id = R.drawable.ic_launcher_foreground)
            return remember {
                TabOptions(
                    index = 2u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {

    }
}