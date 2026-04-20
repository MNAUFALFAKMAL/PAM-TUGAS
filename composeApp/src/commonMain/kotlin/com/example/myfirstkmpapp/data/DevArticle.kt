package com.example.myfirstkmpapp.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DevArticle(
    val id: Int,
    val title: String,
    val description: String,
    @SerialName("social_image") val imageUrl: String,
    val url: String
)