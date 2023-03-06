package uz.gita.bookappShoxruh.screens.Splash.vm

import uz.gita.bookappShoxruh.utils.AppViewModel


interface SplashVM : AppViewModel<Nothing, SplashUiState, Nothing> {}




    data class SplashUiState(
        val version: String
    )

