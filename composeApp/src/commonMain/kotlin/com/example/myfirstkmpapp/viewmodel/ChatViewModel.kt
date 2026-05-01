package com.example.myfirstkmpapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfirstkmpapp.repository.AIRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class ChatUiState(
    val messages: List<ChatMessage> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

data class ChatMessage(
    val text: String,
    val isUser: Boolean,
    val timestamp: Long = 0L
)

class ChatViewModel(private val repository: AIRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState

    fun sendMessage(content: String) {
        if (content.isBlank()) return

        _uiState.value = _uiState.value.copy(
            messages = _uiState.value.messages + ChatMessage(content, true),
            isLoading = true,
            error = null
        )

        viewModelScope.launch {
            repository.sendMessage(content)
                .onSuccess { response ->
                    _uiState.value = _uiState.value.copy(
                        messages = _uiState.value.messages + ChatMessage(response.trim(), false),
                        isLoading = false
                    )
                }
                .onFailure { err ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = err.message ?: "Terjadi kesalahan tidak diketahui"
                    )
                }
        }
    }

    fun resetChat() {
        _uiState.value = ChatUiState()
        repository.clearHistory()
    }
}