package com.example.myfirstkmpapp

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myfirstkmpapp.data.DevArticle
import com.example.myfirstkmpapp.data.DevRepository
import com.example.myfirstkmpapp.data.HttpClientFactory
import com.example.myfirstkmpapp.ui.ArticleDetailScreen
import com.example.myfirstkmpapp.ui.DevScreen
import com.example.myfirstkmpapp.ui.DevViewModel

// Tema Warna Oren (Orange Theme)
val OrangePrimary = Color(0xFFFF9800)
val OrangeSecondary = Color(0xFFFFB74D)
val OrangeBackground = Color(0xFFFFF3E0)
val OrangeSurface = Color(0xFFFFFFFF)

private val OrangeThemeColors = lightColorScheme(
    primary = OrangePrimary,
    secondary = OrangeSecondary,
    background = OrangeBackground,
    surface = OrangeSurface,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black
)

@Composable
fun App() {
    MaterialTheme(colorScheme = OrangeThemeColors) {
        val client = remember { HttpClientFactory.create() }
        val repository = remember { DevRepository(client) }
        val viewModel = viewModel { DevViewModel(repository) }

        var selectedArticle by remember { mutableStateOf<DevArticle?>(null) }

        if (selectedArticle == null) {
            DevScreen(
                viewModel = viewModel,
                onArticleClick = { article -> selectedArticle = article }
            )
        } else {
            ArticleDetailScreen(
                article = selectedArticle!!,
                onNavigateBack = { selectedArticle = null }
            )
        }
    }
}