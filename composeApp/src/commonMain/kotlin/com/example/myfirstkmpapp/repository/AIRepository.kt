package com.example.myfirstkmpapp.repository

import com.example.myfirstkmpapp.service.GeminiService

interface AIRepository {
    suspend fun sendMessage(message: String): Result<String>
    fun clearHistory()
}

class AIRepositoryImpl : AIRepository {
    private val conversationHistory = mutableListOf<Pair<String, Boolean>>()

    override suspend fun sendMessage(message: String): Result<String> {
        conversationHistory.add(message to true)

        val result = GeminiService.ask(message, conversationHistory.toList())

        if (result.isSuccess) {
            val response = result.getOrNull() ?: ""
            conversationHistory.add(response to false)
        } else {
            if (conversationHistory.lastOrNull()?.first?.isNotEmpty() == true && conversationHistory.lastOrNull()?.second == true) {
                conversationHistory.removeLast()
            }
        }

        return result
    }

    override fun clearHistory() {
        conversationHistory.clear()
    }
}