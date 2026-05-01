package com.example.myfirstkmpapp.service

import io.ktor.client.HttpClient
import io.ktor.client.engine.ios.IOS
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

actual val httpClient = HttpClient(IOS) {
    install(ContentNegotiation) { json(Json { ignoreUnknownKeys = true }) }
}
