package com.example.myfirstkmpapp.config

import java.util.Properties
import java.io.File

actual object AppConfig {
    actual val geminiApiKey: String
        get() {
            val props = Properties()
            val localPropsFile = File("local.properties")
            if (localPropsFile.exists()) {
                localPropsFile.inputStream().use { props.load(it) }
            }
            return props.getProperty("GEMINI_API_KEY", "")
        }
}
