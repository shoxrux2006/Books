package uz.gita.bookappShoxruh.screens.Splash

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import org.orbitmvi.orbit.compose.collectAsState
import uz.gita.bookappShoxruh.screens.Splash.vm.SplashUiState
import uz.gita.bookappShoxruh.screens.Splash.vm.SplashVM
import uz.gita.banking.presentation.screen.Splash.vm.impl.SplashVMImpl


class SplashScreen : AndroidScreen() {
    @Composable
    override fun Content() {
        val viewModel: SplashVM = getViewModel<SplashVMImpl>()
        val uiState = viewModel.collectAsState().value
        SplashScreenComponent(uiState = uiState)
    }

    @Composable
    fun SplashScreenComponent(uiState: SplashUiState) {
        val configuration = LocalConfiguration.current
        val screenHeight = configuration.screenHeightDp.dp
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
        ) {
            Spacer(
                Modifier.weight(1f)
            )

            Spacer(Modifier.weight(1f))

            Text(text = "Version ${uiState.version}", Modifier, color = Color.White)
        }
    }

    @Preview
    @Composable
    fun SplashScreenView() {

    }

}
