package org.example.restquiz.controllers.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.jetbrains.annotations.NotNull

data class RegistrationRequest(
    @field:Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")
    val email: String,
    @field:Size(min = 5)
    val password: String
)

data class PostQuizRequest(
    @field:NotNull
    @field:NotBlank
    val title: String,
    @field:NotNull
    @field:NotBlank
    val text: String,
    @field:Size(min = 2)
    val options: List<String>,
    val answer: List<Int>?
)