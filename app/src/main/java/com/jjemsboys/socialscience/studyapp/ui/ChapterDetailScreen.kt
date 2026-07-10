package com.jjemsboys.socialscience.studyapp.ui

import android.content.Intent
import android.net.Uri
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.jjemsboys.socialscience.studyapp.data.BookmarkManager
import com.jjemsboys.socialscience.studyapp.data.ChapterRepository
import com.jjemsboys.socialscience.studyapp.data.QA
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChapterDetailScreen(nav: NavController, id: String) {
    val chapter = ChapterRepository.chapters.find { it.id == id }
    var query by remember { mutableStateOf("") }
    var showPreview by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val bookmarkManager = remember { BookmarkManager(context) }
    val savedIds by bookmarkManager.savedQa.collectAsState(initial = emptySet())

    if (chapter == null) return

    // DRIVE KA DIRECT PREVIEW LINK - FAST LOADING
    val previewUrl = when (chapter.id) {
        "earth_living_planet" -> "https://drive.google.com/file/d/12kjoZTyZ6nQKtq9ZU3CAeBb7pDsHUwZ_/preview"
        "sources" -> "https://drive.google.com/file/d/1vTqL1Stq5z7JQUNJsL6Mc02SFrEVHb9K/preview"
        else -> ""
    }

    // DOWNLOAD KE LIYE NORMAL VIEW LINK
    val downloadUrl = when (chapter.id) {
        "earth_living_planet" -> "https://drive.google.com/file/d/12kjoZTyZ6nQKtq9ZU3CAeBb7pDsHUwZ_/view"
        "sources" -> "https://drive.google.com/file/d/1vTqL1Stq5z7JQUNJsL6Mc02SFrEVHb9K/view"
        else -> ""
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column {
                        Text(
                            chapter.title,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF0F7A6F) // DARK GREEN
                        )
                        Text(
                            chapter.subject,
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF8A7F6B)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { nav.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color(0xFF0F7A6F) // DARK GREEN
                        )
                    }
                },
                actions = {
                    if (previewUrl.isNotEmpty()) {
                        IconButton(onClick = { showPreview = !showPreview }) {
                            Icon(
                                imageVector = if (showPreview) Icons.Default.Close else Icons.Default.PictureAsPdf,
                                contentDescription = if (showPreview) "Close Preview" else "Show Preview",
                                tint = Color(0xFF0F7A6F) // DARK GREEN
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(0xFFFDF6E9))
            )
        },
        containerColor = Color(0xFFFDF6E9)
    ) { padding ->
        if (showPreview && previewUrl.isNotEmpty()) {
            // PDF PREVIEW MODE - ERR_CACHE_MISS FIX
            Column(Modifier.padding(padding).fillMaxSize()) {
                AndroidView(
                    factory = { ctx ->
                        WebView(ctx).apply {
                            webViewClient = WebViewClient()
                            settings.javaScriptEnabled = true
                            settings.setSupportZoom(true)
                            settings.builtInZoomControls = true
                            settings.displayZoomControls = false
                            settings.cacheMode = WebSettings.LOAD_NO_CACHE // CACHE FIX
                            settings.domStorageEnabled = true // STORAGE FIX
                            settings.loadWithOverviewMode = true
                            settings.useWideViewPort = true
                            loadUrl(previewUrl)
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        } else {
            // NORMAL NOTES MODE
            LazyColumn(
                Modifier.padding(padding).padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    OutlinedTextField(
                        value = query,
                        onValueChange = { query = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Search notes...") },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = null,
                                tint = Color(0xFF0F7A6F) // DARK GREEN
                            )
                        },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = Color(0xFF0F7A6F),
    unfocusedBorderColor = Color(0xFF0F7A6F).copy(alpha = 0.5f)
),
),
                        shape = RoundedCornerShape(12.dp)
                    )
                }
                item {
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFCF3)),
                        border = BorderStroke(1.5.dp, Color(0xFF0F7A6F)) // DARK GREEN BORDER
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            AssistChip(
                                onClick = {},
                                label = { Text(chapter.subtitle.uppercase()) },
                                colors = AssistChipDefaults.assistChipColors(
                                    labelColor = Color(0xFF0F7A6F) // DARK GREEN
                                ),
                                border = BorderStroke(1.dp, Color(0xFF0F7A6F))
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                chapter.title,
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF0F7A6F) // DARK GREEN
                            )
                            Text(chapter.desc, color = Color(0xFF8A7F6B))
                        }
                    }
                }
                if (previewUrl.isNotEmpty()) {
                    item {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            // PREVIEW BUTTON
                            OutlinedButton(
                                onClick = { showPreview = true },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = Color(0xFF0F7A6F) // DARK GREEN
                                ),
                                border = BorderStroke(1.5.dp, Color(0xFF0F7A6F))
                            ) {
                                Icon(Icons.Default.Visibility, contentDescription = null)
                                Spacer(Modifier.width(4.dp))
                                Text("Preview PDF", fontWeight = FontWeight.Bold)
                            }
                            // DOWNLOAD BUTTON
                            Button(
                                onClick = {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(downloadUrl))
                                    context.startActivity(intent)
                                },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F7A6F)), // DARK GREEN
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Icon(Icons.Default.Download, contentDescription = null)
                                Spacer(Modifier.width(4.dp))
                                Text("Download", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
                val filtered = chapter.qa.filter {
                    it.q.contains(query, true) || it.a.contains(query, true)
                }
                itemsIndexed(filtered) { _, qa ->
                    ExpandableQACard(
                        qa = qa,
                        isSaved = savedIds.contains(qa.id),
                        onToggleSave = { scope.launch { bookmarkManager.toggleSaveQA(qa.id) } }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandableQACard(
    qa: QA,
    isSaved: Boolean = false,
    onToggleSave: (() -> Unit)? = null
) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        onClick = { expanded = !expanded },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFCF3)),
        border = BorderStroke(1.dp, Color(0xFF0F7A6F).copy(0.3f)) // DARK GREEN BORDER
    ) {
        Column(Modifier.padding(13.dp).animateContentSize()) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "${qa.id} ${qa.q}",
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f),
                    fontSize = 13.sp,
                    lineHeight = 18.sp,
                    color = Color.Black
                )
                if (onToggleSave != null) {
                    IconButton(onClick = onToggleSave) {
                        Icon(
                            if (isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = if (isSaved) "Unsave" else "Save",
                            tint = Color(0xFF0F7A6F) // DARK GREEN
                        )
                    }
                }
                Icon(
                    if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = Color(0xFF0F7A6F) // DARK GREEN
                )
            }
            if (expanded) {
                Spacer(Modifier.height(8.dp))
                Text(
                    qa.a,
                    color = Color(0xFF1E293B),
                    fontSize = 13.sp,
                    lineHeight = 19.sp
                )
            }
        }
    }
}
