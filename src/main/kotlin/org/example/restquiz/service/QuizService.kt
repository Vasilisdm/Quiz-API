package org.example.restquiz.service

import jakarta.transaction.Transactional
import java.util.*
import org.example.restquiz.domain.entities.CompletedQuiz
import org.example.restquiz.domain.entities.Quiz
import org.example.restquiz.repositories.CompletedQuizRepository
import org.example.restquiz.repositories.QuizRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class QuizService(
    private val quizRepository: QuizRepository,
    private val completedQuizRepository: CompletedQuizRepository
) {
    fun save(quiz: Quiz): Quiz = quizRepository.save(quiz)

    fun findByQuizId(id: Long): Optional<Quiz> = quizRepository.findById(id)

    fun findAll(pageable: Pageable): Page<Quiz> = quizRepository.findAll(pageable)

    fun saveCompletedQuiz(completedQuiz: CompletedQuiz) = completedQuizRepository.save(completedQuiz)

    fun findCompletedQuizzesByUsername(username: String, pageable: Pageable) =
        completedQuizRepository.findCompletedQuizsByUsername(username, pageable)

    @Transactional
    fun deleteQuizById(quizId: Long) = quizRepository.deleteQuizById(quizId)
}