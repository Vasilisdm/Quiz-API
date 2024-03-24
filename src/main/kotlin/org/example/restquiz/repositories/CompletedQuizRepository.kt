package org.example.restquiz.repositories

import org.example.restquiz.domain.entities.CompletedQuiz
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface CompletedQuizRepository : PagingAndSortingRepository<CompletedQuiz, Long>,
    CrudRepository<CompletedQuiz, Long> {
    fun findCompletedQuizsByUsername(username: String, pageable: Pageable): Page<CompletedQuiz>
}