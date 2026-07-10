package com.jjemsboys.socialscience.studyapp.ui
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jjemsboys.socialscience.studyapp.data.BookmarkManager
import com.jjemsboys.socialscience.studyapp.data.ChapterRepository
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedScreen(nav: NavController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val bookmarkManager = remember { BookmarkManager(context) }
    val savedIds by bookmarkManager.savedQa.collectAsState(initial = emptySet())
    val allQA = remember { ChapterRepository.getAllQA() }
    val savedQA = allQA.filter { savedIds.contains(it.id) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Saved", fontWeight = FontWeight.ExtraBold) },
                navigationIcon = { IconButton(onClick = { nav.popBackStack() }) { Icon(Icons.Default.ArrowBack, contentDescription = null) } },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(0xFFFDF6E9))
            )
        },
        containerColor = Color(0xFFFDF6E9)
    ) { padding ->
        LazyColumn(Modifier.padding(padding).padding(14.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            if (savedQA.isEmpty()) {
                item {
                    Text(
                        "No saved items yet. Tap the bookmark icon on any question to save it here.",
                        modifier = Modifier.padding(16.dp),
                        color = Color(0xFF8A7F6B)
                    )
                }
            }
            itemsIndexed(savedQA) { _, qa ->
                ExpandableQACard(
                    qa = qa,
                    isSaved = true,
                    onToggleSave = { scope.launch { bookmarkManager.toggleSaveQA(qa.id) } }
                )
            }
        }
    }
}
