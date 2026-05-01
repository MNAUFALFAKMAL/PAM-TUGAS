package com.example.myfirstkmpapp.utils

import kotlinx.coroutines.delay

suspend fun <T> retryWithBackoff(
    maxRetries: Int = 3,
    initialDelay: Long = 1000L,
    backoffFactor: Double = 2.0,
    block: suspend () -> T
): T {
    var currentDelay = initialDelay
    repeat(maxRetries) { attempt ->
        try {
            return block()
        } catch (e: Exception) {
            if (attempt == maxRetries - 1) throw e
            delay(currentDelay)
            currentDelay = (currentDelay * backoffFactor).toLong()
        }
    }
    throw Exception("Retry failed")
}

fun getUserFriendlyError(message: String): String {
    if (message.contains("API Key tidak ditemukan")) return message
    
    return when {
        message.contains("401", ignoreCase = true) || message.contains("invalid", ignoreCase = true) -> "API Key tidak valid. Periksa kembali di Google AI Studio."
        message.contains("403", ignoreCase = true) -> "Akses ditolak (403). Pastikan wilayah Anda didukung Gemini."
        message.contains("429", ignoreCase = true) -> "Terlalu banyak permintaan (429). Mohon tunggu 1 menit."
        message.contains("500", ignoreCase = true) -> "Server AI sedang sibuk. Coba lagi nanti."
        message.contains("timeout", ignoreCase = true) -> "Koneksi timeout. Periksa internet Anda."
        message.contains("404", ignoreCase = true) -> "Error 404: Model tidak ditemukan. Detail: $message"
        else -> "Error: $message"
    }
}
