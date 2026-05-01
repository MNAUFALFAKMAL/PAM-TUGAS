package com.example.myfirstkmpapp.config

actual object AppConfig {
    actual val geminiApiKey: String
        get() = com.example.myfirstkmpapp.BuildConfig.GEMINI_API_KEY
}
