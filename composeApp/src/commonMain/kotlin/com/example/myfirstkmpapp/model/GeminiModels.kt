package com.example.myfirstkmpapp.model

import kotlinx.serialization.Serializable

@Serializable
data class GeminiRequest(
    val contents: List<Content>,
    val generationConfig: GenerationConfig? = null
)

@Serializable
data class Content(
    val parts: List<Part>,
    val role: String = "user"
)

@Serializable
data class Part(val text: String)

@Serializable
data class GenerationConfig(
    val temperature: Double = 0.7,
    val maxOutputTokens: Int = 1000,
    val topP: Double = 0.95
)

@Serializable
data class GeminiResponse(
    val candidates: List<Candidate>? = null,
    val promptFeedback: PromptFeedback? = null
)

@Serializable
data class Candidate(
    val content: Content? = null,
    val finishReason: String? = null,
    val index: Int? = null,
    val safetyRatings: List<SafetyRating>? = null
)

@Serializable
data class PromptFeedback(
    val safetyRatings: List<SafetyRating>? = null
)

@Serializable
data class SafetyRating(
    val category: String,
    val probability: String
)
