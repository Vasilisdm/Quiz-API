package org.example.restquiz.controllers.exceptionHandler.dto


data class EmailAlreadyExists(override val message: String) : RuntimeException()

data class QuizIsNotOwnedByUser(override val message: String) : RuntimeException()