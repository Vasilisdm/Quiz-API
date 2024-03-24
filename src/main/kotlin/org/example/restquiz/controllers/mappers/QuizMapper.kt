package org.example.restquiz.controllers.mappers

import java.util.*
import org.example.restquiz.controllers.dto.PostQuizRequest
import org.example.restquiz.controllers.dto.PostQuizResponse
import org.example.restquiz.controllers.dto.QuizAnswerResponse
import org.example.restquiz.controllers.dto.SolvedQuizResponse
import org.example.restquiz.domain.entities.AppUser
import org.example.restquiz.domain.entities.CompletedQuiz
import org.example.restquiz.domain.entities.Quiz

private const val CORRECT_ANSWER = "Congratulations, you're right!"
private const val WRONG_ANSWER = "Wrong answer! Please, try again."

class QuizMapper {

    companion object {
        fun mapToEntity(request: PostQuizRequest, appUser: AppUser?) = Quiz(
            title = request.title,
            text = request.text,
            options = request.options,
            answers = request.answer,
            appUser = appUser
        )

        fun mapToPostQuizResponse(quiz: Quiz) = PostQuizResponse(
            id = quiz.id,
            title = quiz.title,
            text = quiz.text,
            options = quiz.options
        )

        fun mapToCompletedQuiz(quiz: Quiz, username: String) = CompletedQuiz(
            completedAt = Date(),
            quiz = quiz,
            username = username
        )

        fun mapToQuizAnswerResponse(success: Boolean) = when (success) {
            true -> QuizAnswerResponse(success = true, feedback = CORRECT_ANSWER)
            false -> QuizAnswerResponse(success = false, feedback = WRONG_ANSWER)
        }

        fun mapToSolvedQuizResponse(completedQuiz: CompletedQuiz) = SolvedQuizResponse(
            id = completedQuiz.quiz.id,
            completedAt = completedQuiz.completedAt
        )
    }
}