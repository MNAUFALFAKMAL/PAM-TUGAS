package com.example.myfirstkmpapp.data

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class DevRepository(private val client: HttpClient) {
    // API Artikel Developer (Gratis & Tidak perlu Key)
    private val endpoint = "https://dev.to/api/articles?per_page=20"

    suspend fun fetchArticles(): Result<List<DevArticle>> {
        return try {
            // Karena API Dev.to langsung mengembalikan List JSON (bukan dibungkus object)
            val articles: List<DevArticle> = client.get(endpoint).body()
            Result.success(articles)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}