package com.jjemsboys.socialscience.studyapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.jjemsboys.socialscience.studyapp.data.ChapterRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlashcardSelectorScreen(nav: NavController) {
    val chapters = listOf("all" to "All Chapters - Mixed") + ChapterRepository.chapters.map { it.id to it.title }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Flashcards", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(0xFFFDF6E9))
            )
        },
        containerColor = Color(0xFFFDF6E9)
    ) { padding ->
        LazyColumn(
            Modifier.padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(chapters) { (id, name) ->
                Card(
                    onClick = { nav.navigate("flashcard_play/$id") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFCF3)),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFF0F7A6F)) // DARK GREEN BORDER
                ) {
                    Row(Modifier.padding(20.dp)) {
                        Text(
                            name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color(0xFF0F7A6F) // DARK GREEN TEXT
                        )
                    }
                }
            }
        }
    }
}
