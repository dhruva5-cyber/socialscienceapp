package com.jjemsboys.socialscience.studyapp.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.jjemsboys.socialscience.studyapp.data.ChapterRepository
import kotlinx.coroutines.delay
import kotlin.random.Random

data class QuizQuestion(val q: String, val options: List<String>, val answer: Int)

// 20 questions per chapter - har baar 10 random
val questionPool = mapOf(
    "earth_living_planet" to (1..20).map {
        QuizQuestion(
            "Earth Q$it: What is the largest continent?",
            listOf("Asia","Africa","Europe","Australia"),
            0
        )
    },
    "sources" to (1..20).map {
        QuizQuestion(
            "Sources Q$it: Which is a primary source?",
            listOf("Textbook","Newspaper","Diary","Documentary"),
            2
        )
    }
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizSelectorScreen(nav: NavController, mode: String) {
    val chapters = listOf("all" to "All Chapters - Random Mix") +
        ChapterRepository.chapters.map { it.id to it.title }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        if(mode=="test") "Test Mode" else "Practice Quiz",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F7A6F) // DARK GREEN
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { nav.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, null, tint = Color(0xFF0F7A6F))
                    }
                },
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
                    onClick = { nav.navigate("quiz_play/$id/$mode") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFCF3)),
                    border = BorderStroke(1.5.dp, Color(0xFF0F7A6F)) // DARK GREEN BORDER
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(nav: NavController, chapterId: String, mode: String) {
    // Random 10 questions from pool of 20
    val questions = remember {
        val base = if(chapterId=="all") {
            questionPool.values.flatten()
        } else {
            questionPool[chapterId]?: emptyList()
        }
        base.shuffled().take(10)
    }

    var currentIdx by remember { mutableStateOf(0) }
    var score by remember { mutableStateOf(0) }
    var selectedIdx by remember { mutableStateOf<Int?>(null) }
    var timeLeft by remember { mutableStateOf(30) }
    var answered by remember { mutableStateOf(false) }

    // Timer sirf Test mode me
    LaunchedEffect(currentIdx, mode) {
        selectedIdx = null
        answered = false
        if(mode=="test") {
            timeLeft = 30
            while(timeLeft > 0 &&!answered) {
                delay(1000)
                timeLeft--
            }
            if(!answered && currentIdx < questions.size) {
                currentIdx++
            }
        }
    }

    if(questions.isEmpty()) {
        Column(Modifier.fillMaxSize().padding(32.dp), verticalArrangement = Arrangement.Center) {
            Text("No questions available", color = Color(0xFF0F7A6F))
            Button(
                onClick = { nav.popBackStack() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F7A6F))
            ) { Text("Back") }
        }
        return
    }

    if(currentIdx >= questions.size) {
        // RESULT SCREEN
        Scaffold(containerColor = Color(0xFFFDF6E9)) { padding ->
            Column(
                Modifier.padding(padding).padding(32.dp).fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "Quiz Complete!",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F7A6F)
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    "Your Score: $score / 10",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color(0xFF0F7A6F)
                )
                Text(
                    "${(score * 10)}%",
                    style = MaterialTheme.typography.titleLarge,
                    color = if(score >= 7) Color(0xFF0F7A6F) else Color(0xFFF44336)
                )
                Spacer(Modifier.height(24.dp))
                Button(
                    onClick = { nav.popBackStack() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F7A6F))
                ) {
                    Text("Back to Home", fontWeight = FontWeight.Bold)
                }
            }
        }
        return
    }

    val question = questions[currentIdx]

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        if(mode=="test") "Test" else "Quiz",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F7A6F)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { nav.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, null, tint = Color(0xFF0F7A6F))
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(0xFFFDF6E9))
            )
        },
        containerColor = Color(0xFFFDF6E9)
    ) { padding ->
        Column(Modifier.padding(padding).padding(16.dp)) {
            // Timer bar for Test mode
            if(mode=="test") {
                LinearProgressIndicator(
                    progress =  timeLeft / 30f ,
                    modifier = Modifier.fillMaxWidth().height(8.dp),
                    color = if(timeLeft < 10) Color.Red else Color(0xFF0F7A6F),
                    trackColor = Color(0xFF0F7A6F).copy(0.2f)
                )
                Text(
                    "Time: ${timeLeft}s",
                    Modifier.padding(vertical = 8.dp),
                    color = Color(0xFF0F7A6F),
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                "Question ${currentIdx + 1}/10",
                style = MaterialTheme.typography.labelLarge,
                color = Color.Gray
            )
            Spacer(Modifier.height(8.dp))
            Text(
                question.q,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(Modifier.height(20.dp))

            // Options - Click pe turant DARK GREEN/RED
            question.options.forEachIndexed { idx, option ->
                val bgColor = when {
                  !answered -> Color(0xFFFFFCF3)
                    idx == question.answer -> Color(0xFF0F7A6F) // DARK GREEN - correct
                    idx == selectedIdx -> Color(0xFFF44336) // RED - wrong
                    else -> Color(0xFFFFFCF3)
                }
                val textColor = when {
                  !answered -> Color.Black
                    idx == question.answer || idx == selectedIdx -> Color.White
                    else -> Color.Black
                }
                val borderColor = if(selectedIdx == idx) Color.Black else Color(0xFF0F7A6F)

                Card(
                    onClick = {
                        if(!answered) {
                            selectedIdx = idx
                            answered = true
                            if(idx == question.answer) score++
                        }
                    },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
                    colors = CardDefaults.cardColors(containerColor = bgColor),
                    border = BorderStroke(2.dp, borderColor),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        option,
                        Modifier.padding(16.dp),
                        fontWeight = FontWeight.Medium,
                        color = textColor
                    )
                }
            }

            if(answered) {
                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = { currentIdx++ },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F7A6F))
                ) {
                    Text(
                        if(currentIdx == 9) "Finish" else "Next Question",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
