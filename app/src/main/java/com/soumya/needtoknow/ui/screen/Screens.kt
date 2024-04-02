package com.soumya.needtoknow.ui.screen

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Details : Screen("details/{planetInfo}")
}