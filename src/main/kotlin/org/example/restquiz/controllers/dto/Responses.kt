package org.example.restquiz.controllers.dto

import java.util.*

data class QuizAnswerResponse(
    val success: Boolean,
    val feedback: String
)

data class PostQuizResponse(
    val id: Long,
    val title: String,
    val text: String,
    val options: List<String>,
)

data class QuizAnswer(
    val answer: List<Int>
)

data class SolvedQuizResponse(
    val id: Long,
    val completedAt: Date
)