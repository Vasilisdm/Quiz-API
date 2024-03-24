package org.example.restquiz.repositories

import org.example.restquiz.domain.entities.Quiz
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface QuizRepository : PagingAndSortingRepository<Quiz, Long>, CrudRepository<Quiz, Long> {
    fun deleteQuizById(quizId: Long)
}