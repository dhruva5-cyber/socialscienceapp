package com.jjemsboys.socialscience.studyapp.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jjemsboys.socialscience.studyapp.data.ChapterRepository
import com.jjemsboys.socialscience.studyapp.data.QA

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlashcardScreen(nav: NavController, chapterId: String) {
    val cards: List<QA> = remember(chapterId) {
        if (chapterId == "all") {
            ChapterRepository.getAllQA()
        } else {
            ChapterRepository.chapters.find { it.id == chapterId }?.qa ?: emptyList()
        }
    }

    var currentIdx by remember(chapterId) { mutableStateOf(0) }
    var flipped by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(targetValue = if (flipped) 180f else 0f, label = "flashcard_flip")

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Flashcards", fontWeight = FontWeight.Bold, color = Color(0xFF0F7A6F)) },
                navigationIcon = {
                    IconButton(onClick = { nav.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color(0xFF0F7A6F))
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(0xFFFDF6E9))
            )
        },
        containerColor = Color(0xFFFDF6E9)
    ) { padding ->
        if (cards.isEmpty()) {
            Column(
                Modifier.padding(padding).fillMaxSize().padding(32.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("No flashcards available", color = Color(0xFF0F7A6F))
                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = { nav.popBackStack() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F7A6F))
                ) { Text("Back") }
            }
            return@Scaffold
        }

        val card = cards[currentIdx]

        Column(
            Modifier.padding(padding).fillMaxSize().padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Card ${currentIdx + 1} / ${cards.size}",
                style = MaterialTheme.typography.labelLarge,
                color = Color(0xFF8A7F6B)
            )
            Spacer(Modifier.height(8.dp))
            LinearProgressIndicator(
                progress =  (currentIdx + 1) / cards.size.toFloat() ,
                modifier = Modifier.fillMaxWidth().height(6.dp),
                color = Color(0xFF0F7A6F),
                trackColor = Color(0xFF0F7A6F).copy(alpha = 0.2f)
            )
            Spacer(Modifier.height(24.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .graphicsLayer {
                        rotationY = rotation
                        cameraDistance = 12f * density
                    }
                    .clickable { flipped = !flipped },
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFCF3)),
                border = BorderStroke(1.5.dp, Color(0xFF0F7A6F))
            ) {
                Box(Modifier.fillMaxSize().padding(24.dp), contentAlignment = Alignment.Center) {
                    if (rotation <= 90f) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                "Q. ${card.id}",
                                style = MaterialTheme.typography.labelMedium,
                                color = Color(0xFF8A7F6B)
                            )
                            Spacer(Modifier.height(12.dp))
                            Text(
                                card.q,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0F7A6F)
                            )
                            Spacer(Modifier.height(16.dp))
                            Text(
                                "Tap to reveal answer",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFF8A7F6B)
                            )
                        }
                    } else {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.graphicsLayer { rotationY = 180f }
                        ) {
                            Text(
                                "Answer",
                                style = MaterialTheme.typography.labelMedium,
                                color = Color(0xFF8A7F6B)
                            )
                            Spacer(Modifier.height(12.dp))
                            Text(
                                card.a,
                                style = MaterialTheme.typography.titleMedium,
                                color = Color(0xFF1E293B)
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        flipped = false
                        currentIdx = if (currentIdx == 0) cards.size - 1 else currentIdx - 1
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF0F7A6F)),
                    border = BorderStroke(1.5.dp, Color(0xFF0F7A6F))
                ) { Text("Previous") }

                Button(
                    onClick = {
                        flipped = false
                        currentIdx = if (currentIdx == cards.size - 1) 0 else currentIdx + 1
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F7A6F))
                ) { Text("Next", fontWeight = FontWeight.Bold) }
            }
        }
    }
}
