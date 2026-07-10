package com.jjemsboys.socialscience.studyapp.ui

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IndexScreen(nav: NavController) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Row(Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Menu", style = MaterialTheme.typography.titleLarge, color = Color(0xFF0F7A6F))
                    IconButton(onClick = { scope.launch { drawerState.close() } }) {
                        Icon(Icons.Default.Close, null, tint = Color(0xFF0F7A6F))
                    }
                }
                NavigationDrawerItem(
                    label = { Text("Chapter Notes") },
                    selected = false,
                    onClick = { nav.navigate("chapters"); scope.launch { drawerState.close() } },
                    colors = NavigationDrawerItemDefaults.colors(unselectedIconColor = Color(0xFF0F7A6F))
                )
                NavigationDrawerItem(label = { Text("Quiz") }, selected = false, onClick = { nav.navigate("quiz"); scope.launch { drawerState.close() } })
                NavigationDrawerItem(label = { Text("Test") }, selected = false, onClick = { nav.navigate("test"); scope.launch { drawerState.close() } })
                NavigationDrawerItem(label = { Text("Flashcards") }, selected = false, onClick = { nav.navigate("flashcard"); scope.launch { drawerState.close() } })
                NavigationDrawerItem(label = { Text("Saved") }, selected = false, onClick = { nav.navigate("saved"); scope.launch { drawerState.close() } })
            }
        }
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("SOCIAL SCIENCE", fontWeight = FontWeight.ExtraBold, color = Color(0xFF0F7A6F)) },
                    actions = {
                        IconButton(onClick = { nav.navigate("search") }) { Icon(Icons.Default.Search, null, tint = Color(0xFF0F7A6F)) }
                        IconButton(onClick = { scope.launch { drawerState.open() } }) { Icon(Icons.Default.Menu, null, tint = Color(0xFF0F7A6F)) }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(0xFFFDF6E9))
                )
            },
            bottomBar = {
                NavigationBar(containerColor = Color(0xFF101C36)) {
                    listOf("Learn" to Icons.Default.School, "Quiz" to Icons.Default.Help, "Test" to Icons.Default.Description, "Flashcard" to Icons.Default.Style).forEachIndexed { i, (label, icon) ->
                        NavigationBarItem(
                            selected = i==0,
                            onClick = { when(i){1->nav.navigate("quiz"); 2->nav.navigate("test"); 3->nav.navigate("flashcard")} },
                            icon = { Icon(icon, null) }, label = { Text(label) },
                            colors = NavigationBarItemDefaults.colors(selectedIconColor = Color(0xFF0F7A6F), unselectedIconColor = Color.White, indicatorColor = Color(0xFF0F7A6F).copy(0.2f))
                        )
                    }
                }
            },
            containerColor = Color(0xFFFDF6E9)
        ) { padding ->
            Column(Modifier.padding(padding).padding(12.dp)) {
                val cards = listOf(
                    Triple("Saved", "View saved", Color(0xFF0F7A6F)),
                    Triple("Download Notes", "Get PDFs", Color(0xFF0F7A6F)),
                    Triple("Chapter Notes", "Summaries", Color(0xFF0F7A6F)),
                    Triple("Settings", "Customize", Color(0xFF0F7A6F))
                )
                LazyVerticalGrid(columns = GridCells.Fixed(2), verticalArrangement = Arrangement.spacedBy(10.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(cards.size) { idx ->
                        val (title, desc, color) = cards[idx]
                        Card(Modifier.fillMaxWidth().clickable {
                            when(title) {
                                "Chapter Notes" -> nav.navigate("chapters")
                                "Saved" -> nav.navigate("saved")
                                "Download Notes" -> nav.navigate("chapters")
                                "Settings" -> Toast.makeText(context, "Settings coming soon", Toast.LENGTH_SHORT).show()
                            }
                        }, shape = RoundedCornerShape(18.dp)) {
                            Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                                Box(Modifier.size(50.dp).clip(CircleShape).background(color)) {
                                    val icon = listOf(Icons.Default.Bookmark, Icons.Default.Download, Icons.Default.Description, Icons.Default.Settings)[idx]
                                    Icon(icon, null, Modifier.align(Alignment.Center), tint = Color.White)
                                }
                                Spacer(Modifier.width(10.dp))
                                Column(Modifier.weight(1f)) {
                                    Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF0F7A6F))
                                    Text(desc, fontSize = 11.sp, color = Color(0xFF8A7F6B))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
