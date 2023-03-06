package uz.gita.bookappShoxruh

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.lifecycle.lifecycleScope
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.bookappShoxruh.navigation.NavigationHandler
import uz.gita.bookappShoxruh.screens.Splash.SplashScreen
import uz.gita.bookappShoxruh.ui.theme.BooksTheme
import javax.inject.Inject


@Suppress("OPT_IN_IS_NOT_ENABLED")
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var navigationHandler: NavigationHandler


    @OptIn(ExperimentalAnimationApi::class)
    @SuppressLint("FlowOperatorInvokedInComposition", "CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BooksTheme() {
                Navigator(screen = SplashScreen(), onBackPressed = {
                    true
                }) { navigator ->
                    navigationHandler.navStack.onEach {
                        it.invoke(navigator)
                    }.launchIn(lifecycleScope)
                    SlideTransition(navigator)
                }
            }
        }
    }

}