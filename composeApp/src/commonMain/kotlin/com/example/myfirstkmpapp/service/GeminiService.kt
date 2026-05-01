package com.example.myfirstkmpapp.service

import com.example.myfirstkmpapp.config.AppConfig
import com.example.myfirstkmpapp.model.Content
import com.example.myfirstkmpapp.model.GeminiRequest
import com.example.myfirstkmpapp.model.GeminiResponse
import com.example.myfirstkmpapp.model.Part
import com.example.myfirstkmpapp.utils.getUserFriendlyError
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess

expect val httpClient: HttpClient

object GeminiService {

    suspend fun ask(prompt: String, history: List<Pair<String, Boolean>> = emptyList()): Result<String> {
        val apiKey = AppConfig.geminiApiKey.trim().removeSurrounding("\"")

        if (apiKey.isBlank()) {
            return Result.failure(Exception("API Key kosong."))
        }

        return try {
            val modelId = "gemini-2.5-flash"

            val request = GeminiRequest(
                contents = listOf(
                    Content(role = "user", parts = listOf(Part(text = "Jawab dalam Bahasa Indonesia: $prompt")))
                )
            )

            val httpResponse = httpClient.post(
                "https://generativelanguage.googleapis.com/v1beta/models/$modelId:generateContent?key=$apiKey"
            ) {
                contentType(ContentType.Application.Json)
                setBody(request)
            }

            val bodyText = httpResponse.bodyAsText()

            if (httpResponse.status.value == 404) {
                return retryWithAlternative(apiKey, prompt)
            }

            if (!httpResponse.status.isSuccess()) {
                throw Exception("Error ${httpResponse.status.value}: $bodyText")
            }

            val data = httpResponse.body<GeminiResponse>()
            val text = data.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
                ?: throw Exception("Respon kosong")

            Result.success(text)
        } catch (e: Exception) {
            Result.failure(Exception(getUserFriendlyError(e.message ?: "Gagal")))
        }
    }

    private suspend fun retryWithAlternative(apiKey: String, prompt: String): Result<String> {
        return try {
            val httpResponse = httpClient.post(
                "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash-lite:generateContent?key=$apiKey"
            ) {
                contentType(ContentType.Application.Json)
                setBody(GeminiRequest(contents = listOf(Content(role = "user", parts = listOf(Part(text = prompt))))))
            }
            val data = httpResponse.body<GeminiResponse>()
            val text = data.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
                ?: throw Exception("Gagal di kedua model")
            Result.success(text)
        } catch (e: Exception) {
            Result.failure(Exception("Model tidak ditemukan di akun Anda. Periksa apakah API Key sudah benar."))
        }
    }
}