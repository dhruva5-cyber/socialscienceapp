package com.jjemsboys.socialscience.studyapp.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.jjemsboys.socialscience.studyapp.data.BookmarkManager
import com.jjemsboys.socialscience.studyapp.data.ChapterRepository
import com.jjemsboys.socialscience.studyapp.data.QA
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(nav: NavController) {
    var query by remember { mutableStateOf("") }
    val allQA = remember { ChapterRepository.getAllQA() }
    val results = allQA.filter { it.q.contains(query, true) || it.a.contains(query, true) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val bookmarkManager = remember { BookmarkManager(context) }
    val savedIds by bookmarkManager.savedQa.collectAsState(initial = emptySet())

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Search", fontWeight = FontWeight.ExtraBold) },
                navigationIcon = { IconButton(onClick = { nav.popBackStack() }) { Icon(Icons.Default.ArrowBack, contentDescription = null) } },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(0xFFFDF6E9))
            )
        },
        containerColor = Color(0xFFFDF6E9)
    ) { padding ->
        LazyColumn(Modifier.padding(padding).padding(14.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            item {
                OutlinedTextField(
                    value = query, onValueChange = { query = it }, modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Search all notes...") }, leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }, singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
    focusedBorderColor = Color(0xFF0F7A6F),
    unfocusedBorderColor = Color(0xFF0F7A6F).copy(0.5f)
)
            }
            if (query.isNotEmpty()) {
                item {
                    Text("${results.size} results found", style = MaterialTheme.typography.labelMedium, color = Color(0xFF8A7F6B), modifier = Modifier.padding(vertical = 4.dp))
                }
                itemsIndexed(results) { _, qa ->
                    ExpandableQACard(
                        qa = qa,
                        isSaved = savedIds.contains(qa.id),
                        onToggleSave = { scope.launch { bookmarkManager.toggleSaveQA(qa.id) } }
                    )
                }
            } else {
                item {
                    Box(Modifier.fillMaxWidth().padding(top = 60.dp), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.Search, contentDescription = null, modifier = Modifier.size(64.dp), tint = Color(0xFF8A7F6B))
                            Spacer(Modifier.height(16.dp))
                            Text("Search across all chapters", color = Color(0xFF8A7F6B))
                        }
                    }
                }
            }
        }
    }
}
