// com/example/entrenet/model/Entrepreneur.kt
package com.example.entrenet.model

data class Entrepreneur(
    val name: String,
    val skills: List<String>,
    val place: String,
    var isSelected: Boolean = false
)
