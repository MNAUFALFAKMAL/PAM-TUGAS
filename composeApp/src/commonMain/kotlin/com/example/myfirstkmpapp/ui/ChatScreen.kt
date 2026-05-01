package com.example.myfirstkmpapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfirstkmpapp.viewmodel.ChatMessage
import com.example.myfirstkmpapp.viewmodel.ChatViewModel
import com.example.myfirstkmpapp.repository.AIRepositoryImpl

private val OrangePrimary = Color(0xFFF57C00)
private val OrangeLight = Color(0xFFFFE0B2)
private val OrangeDark = Color(0xFFE65100)
private val GraySurface = Color(0xFFF5F5F5)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen() {
    val viewModel = remember { ChatViewModel(AIRepositoryImpl()) }
    val uiState by viewModel.uiState.collectAsState()
    var messageText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()

    LaunchedEffect(uiState.messages.size) {
        if (uiState.messages.isNotEmpty()) {
            listState.animateScrollToItem(uiState.messages.size - 1)
        }
    }

    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = OrangePrimary,
            onPrimary = Color.White,
            primaryContainer = OrangeLight,
            onPrimaryContainer = OrangeDark,
            surface = GraySurface,
            background = Color.White,
        )
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            "Gemini AI Assistant",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = OrangePrimary
                    )
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(GraySurface)
            ) {
                // Messages List
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    contentPadding = PaddingValues(vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.messages) { message ->
                        MessageBubble(message)
                    }

                    if (uiState.isLoading) {
                        item {
                            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart) {
                                TypingIndicator()
                            }
                        }
                    }

                    uiState.error?.let { error ->
                        item {
                            ErrorBanner(error)
                        }
                    }
                }

                Surface(
                    tonalElevation = 8.dp,
                    shadowElevation = 8.dp,
                    color = Color.White
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                            .navigationBarsPadding()
                            .imePadding(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = messageText,
                            onValueChange = { messageText = it },
                            modifier = Modifier.weight(1f),
                            placeholder = { Text("Tulis pesan...", color = Color.Gray) },
                            shape = RoundedCornerShape(24.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = OrangePrimary,
                                unfocusedBorderColor = Color.LightGray,
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent
                            ),
                            maxLines = 4
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        FloatingActionButton(
                            onClick = {
                                if (messageText.isNotBlank() && !uiState.isLoading) {
                                    viewModel.sendMessage(messageText)
                                    messageText = ""
                                }
                            },
                            containerColor = if (messageText.isNotBlank()) OrangePrimary else Color.LightGray,
                            contentColor = Color.White,
                            shape = RoundedCornerShape(50),
                            modifier = Modifier.size(48.dp),
                            elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp)
                        ) {
                            Text("➤", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MessageBubble(message: ChatMessage) {
    val isUser = message.isUser
    val alignment = if (isUser) Alignment.End else Alignment.Start
    val bubbleColor = if (isUser) OrangePrimary else Color.White
    val textColor = if (isUser) Color.White else Color.Black
    val shape = if (isUser) {
        RoundedCornerShape(16.dp, 16.dp, 2.dp, 16.dp)
    } else {
        RoundedCornerShape(16.dp, 16.dp, 16.dp, 2.dp)
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = alignment
    ) {
        Surface(
            color = bubbleColor,
            shape = shape,
            shadowElevation = 1.dp
        ) {
            Text(
                text = message.text,
                color = textColor,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                fontSize = 15.sp,
                lineHeight = 20.sp
            )
        }
    }
}

@Composable
fun TypingIndicator() {
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(16.dp, 16.dp, 16.dp, 2.dp),
        shadowElevation = 1.dp
    ) {
        Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
            Text("AI sedang berpikir...", fontSize = 13.sp, color = Color.Gray)
        }
    }
}

@Composable
fun ErrorBanner(message: String) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE)),
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = message,
            color = Color(0xFFC62828),
            modifier = Modifier.padding(12.dp),
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
