package com.jjemsboys.socialscience.studyapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.jjemsboys.socialscience.studyapp.data.ChapterRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChaptersScreen(nav: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Column { Text("Chapter Notes", fontWeight = FontWeight.ExtraBold); Text("History • Geography", style = MaterialTheme.typography.labelSmall) } },
                navigationIcon = { IconButton(onClick = { nav.popBackStack() }) { Icon(Icons.Default.ArrowBack, contentDescription = null) } },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(0xFFFDF6E9))
            )
        },
        containerColor = Color(0xFFFDF6E9)
    ) { padding ->
        LazyColumn(Modifier.padding(padding).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            ChapterRepository.chapters.forEach { ch ->
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth().clickable { nav.navigate("chapter/${ch.id}") },
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFCF3))
                    ) {
                        Row(Modifier.padding(15.dp), verticalAlignment = Alignment.CenterVertically) {
                            Box(Modifier.size(54.dp).clip(CircleShape).background(Brush.radialGradient(listOf(Color(0xFF7A8CF0), Color(0xFF2E3A9C))))){
                                Icon(Icons.Default.MenuBook, contentDescription = null, tint = Color.White, modifier = Modifier.align(Alignment.Center))
                            }
                            Spacer(Modifier.width(13.dp))
                            Column(Modifier.weight(1f)) {
                                Text(ch.title, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
                                Text(ch.subtitle, style = MaterialTheme.typography.bodySmall, color = Color(0xFF8A7F6B))
                                Text(ch.desc, style = MaterialTheme.typography.bodySmall, maxLines = 2)
                                Spacer(Modifier.height(4.dp))
                                Text(ch.subject, style = MaterialTheme.typography.labelSmall, color = Color(0xFF6B6B7A))
                            }
                            Icon(Icons.Default.ChevronRight, contentDescription = null)
                        }
                    }
                }
            }
        }
    }
}
