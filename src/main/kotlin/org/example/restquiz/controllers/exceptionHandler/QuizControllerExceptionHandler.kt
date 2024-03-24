package org.example.restquiz.controllers.exceptionHandler

import org.example.restquiz.controllers.exceptionHandler.dto.EmailAlreadyExists
import org.example.restquiz.controllers.exceptionHandler.dto.QuizIsNotOwnedByUser
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class QuizControllerExceptionHandler {

    @ExceptionHandler(EmailAlreadyExists::class)
    fun handleExistingEmail(e: EmailAlreadyExists, request: WebRequest): ResponseEntity<String> =
        ResponseEntity.badRequest().build()

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleFailingValidationOfUserRegistrationRequest(
        e: MethodArgumentNotValidException, request: WebRequest
    ): ResponseEntity<String> = ResponseEntity.badRequest().build()

    @ExceptionHandler(QuizIsNotOwnedByUser::class)
    fun handleUserNotOwingTheQuiz(
        e: QuizIsNotOwnedByUser, request: WebRequest
    ): ResponseEntity<String> = ResponseEntity(HttpStatus.FORBIDDEN)
}