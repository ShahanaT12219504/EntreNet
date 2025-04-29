package com.example.entrenet // Adjust package name if needed

data class Part(
    val text: String? = null, // Make nullable as per API structure
    // Add other properties like inline_data if you handle images
    // val inline_data: InlineData? = null
)

// If you need InlineData for images:
/*
data class InlineData(
    val mime_type: String,
    val data: String // Base64 encoded data
)
*/