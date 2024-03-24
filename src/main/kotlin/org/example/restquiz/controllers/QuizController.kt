package org.example.restquiz.controllers

import jakarta.validation.Valid
import kotlin.jvm.optionals.getOrNull
import org.example.restquiz.controllers.dto.PostQuizRequest
import org.example.restquiz.controllers.dto.PostQuizResponse
import org.example.restquiz.controllers.dto.QuizAnswer
import org.example.restquiz.controllers.dto.QuizAnswerResponse
import org.example.restquiz.controllers.dto.RegistrationRequest
import org.example.restquiz.controllers.dto.SolvedQuizResponse
import org.example.restquiz.controllers.exceptionHandler.dto.EmailAlreadyExists
import org.example.restquiz.controllers.exceptionHandler.dto.QuizIsNotOwnedByUser
import org.example.restquiz.controllers.mappers.QuizMapper
import org.example.restquiz.controllers.mappers.UserMapper
import org.example.restquiz.service.QuizService
import org.example.restquiz.service.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class QuizController(
    private val quizService: QuizService,
    private val userService: UserService,
    private val passwordEncoder: PasswordEncoder
) {
    private val logger: Logger = LoggerFactory.getLogger(QuizController::class.java)

    @DeleteMapping(path = ["/quizzes/{id}"])
    fun deleteQuiz(@PathVariable id: Long, @AuthenticationPrincipal details: UserDetails): ResponseEntity<String> {
        logger.info("Received request to delete quiz: $id from user: ${details.username}")
        val user = userService.findUserByEmail(details.username)

        val quiz = quizService.findByQuizId(id).getOrNull() ?: return ResponseEntity.notFound().build()

        if (quiz.appUser != user) {
            throw QuizIsNotOwnedByUser("The quiz doesn't belong to the current user")
        }

        quizService.deleteQuizById(quizId = quiz.id)

        return ResponseEntity.noContent().build()
    }

    @PostMapping(path = ["/register"], produces = ["application/json"])
    fun register(@RequestBody @Valid registrationRequest: RegistrationRequest): ResponseEntity<String> {
        if (userService.findUserByEmail(registrationRequest.email) != null) {
            throw EmailAlreadyExists(message = "User tried to register with the same email: ${registrationRequest.email}")
        }

        userService.save(
            UserMapper.mapToUser(
                registrationRequest, passwordEncoder.encode(registrationRequest.password)
            )
        )

        return ResponseEntity.ok().build()
    }

    @PostMapping(path = ["/quizzes"], produces = ["application/json"])
    fun postQuiz(
        @RequestBody @Valid request: PostQuizRequest,
        @AuthenticationPrincipal details: UserDetails
    ): ResponseEntity<PostQuizResponse> {
        logger.info("Received request to post a quiz from user: ${details.username}")
        val appUser = userService.findUserByEmail(details.username)

        val quiz = quizService.save(QuizMapper.mapToEntity(request, appUser))

        return ResponseEntity.ok().body(QuizMapper.mapToPostQuizResponse(quiz))
    }

    @GetMapping(path = ["/quizzes/{id}"], produces = ["application/json"])
    fun getQuizById(
        @PathVariable id: Long,
        @AuthenticationPrincipal details: UserDetails
    ): ResponseEntity<PostQuizResponse> {
        logger.info("Received request to search for quiz: $id, from user: ${details.username}")
        val quiz = quizService.findByQuizId(id).getOrNull()

        return if (quiz != null) {
            ResponseEntity.ok().body(QuizMapper.mapToPostQuizResponse(quiz))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping(path = ["/quizzes"], produces = ["application/json"])
    fun getAllQuizzes(@RequestParam page: Int, @AuthenticationPrincipal details: UserDetails): Page<PostQuizResponse> {
        logger.info("Received request to retrieve quizzes for page: $page, from user: ${details.username}")
        val pageable: Pageable = PageRequest.of(page, 10)
        val availableQuizzes = quizService.findAll(pageable).map { QuizMapper.mapToPostQuizResponse(it) }

        return availableQuizzes
    }

    @PostMapping(path = ["/quizzes/{id}/solve"], produces = ["application/json"])
    fun solveQuiz(
        @PathVariable id: Long,
        @RequestBody quizAnswer: QuizAnswer,
        @AuthenticationPrincipal details: UserDetails
    ): ResponseEntity<QuizAnswerResponse> {
        logger.info("User: ${details.username} is trying to solve the quiz: $id by providing this answer: $quizAnswer")
        val quizToSolve = quizService.findByQuizId(id).getOrNull() ?: return ResponseEntity.notFound().build()

        return if ((quizToSolve.answers != null && quizToSolve.answers.toList() == quizAnswer.answer) ||
            (quizToSolve.answers.isNullOrEmpty() && quizAnswer.answer.isEmpty())
        ) {
            quizService.saveCompletedQuiz(
                QuizMapper.mapToCompletedQuiz(quiz = quizToSolve, username = details.username)
            )

            ResponseEntity.ok().body(QuizMapper.mapToQuizAnswerResponse(success = true))
        } else {
            ResponseEntity.ok().body(QuizMapper.mapToQuizAnswerResponse(success = false))
        }
    }

    @GetMapping(path = ["quizzes/completed"], produces = ["application/json"])
    fun getCompletedQuizzesPerUser(
        @RequestParam page: Int,
        @AuthenticationPrincipal details: UserDetails
    ): Page<SolvedQuizResponse> {
        logger.info("Received request to get completed quizzes for page: $page, from user: ${details.username}")
        val pageable = PageRequest.of(page, 10, Sort.by("completedAt").descending())
        val existingUserSolvedQuizzes =
            quizService.findCompletedQuizzesByUsername(username = details.username, pageable)

        val solvedUserQuizzesResponse = existingUserSolvedQuizzes.map { QuizMapper.mapToSolvedQuizResponse(it) }

        return solvedUserQuizzesResponse
    }

}