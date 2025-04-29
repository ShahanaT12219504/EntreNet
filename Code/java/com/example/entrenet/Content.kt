package com.example.entrenet // Adjust package name if needed

data class Content(
    val parts: List<Part>? = null, // Make nullable
    val role: String? = null // Role might be "user" or "model"
)