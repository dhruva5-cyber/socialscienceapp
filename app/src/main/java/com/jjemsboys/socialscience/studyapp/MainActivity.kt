package com.jjemsboys.socialscience.studyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jjemsboys.socialscience.studyapp.data.BookmarkManager
import com.jjemsboys.socialscience.studyapp.ui.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
val bookmarkManager = remember { BookmarkManager(context) }
val systemDark = isSystemInDarkTheme()

// Agar DataStore me value null hai (first launch), toh systemDark use hoga
val isDarkModeSetting by bookmarkManager.isDarkMode.collectAsState(initial = null)
val finalDarkMode = isDarkModeSetting ?: systemDark

JjemsTheme(darkTheme = finalDarkMode) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        AppNav()
    }
}
        }
    }
}

@Composable
fun AppNav() {
    val nav = rememberNavController()
    NavHost(nav, startDestination = "index") {
        composable("index") { IndexScreen(nav) }
        composable("chapters") { ChaptersScreen(nav) }
        composable("chapter/{id}") { backStack ->
            ChapterDetailScreen(nav, backStack.arguments?.getString("id") ?: "")
        }
        composable("search") { SearchScreen(nav) }
        composable("saved") { SavedScreen(nav) }

        // QUIZ ROUTES
        composable("quiz") { QuizSelectorScreen(nav, mode = "quiz") }
        composable("test") { QuizSelectorScreen(nav, mode = "test") }
        composable("quiz_play/{id}/{mode}") { backStack ->
            val id = backStack.arguments?.getString("id") ?: ""
            val mode = backStack.arguments?.getString("mode") ?: "quiz"
            QuizScreen(nav, id, mode)
        }

        // FLASHCARD ROUTES
        composable("flashcard") { FlashcardSelectorScreen(nav) }
        composable("flashcard_play/{id}") { backStack ->
            FlashcardScreen(nav, backStack.arguments?.getString("id") ?: "")
        }
    }
}
