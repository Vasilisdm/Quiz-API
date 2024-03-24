package org.example.restquiz.repositories

import org.example.restquiz.domain.entities.AppUser
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<AppUser, Long> {
    fun findUserByEmail(email: String): AppUser?
    fun findUserByUsername(username: String): AppUser?
}