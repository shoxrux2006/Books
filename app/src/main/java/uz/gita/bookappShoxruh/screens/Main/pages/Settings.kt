package uz.gita.banking.presentation.screen.Main.pages

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import uz.gita.bookappShoxruh.R

class Settings: Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Settings"
            val icon = painterResource(id = R.drawable.ic_launcher_background)

            return remember {
                TabOptions(
                    index = 3u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {

    }
}