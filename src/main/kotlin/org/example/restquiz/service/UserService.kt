package org.example.restquiz.service

import org.example.restquiz.domain.entities.AppUser
import org.example.restquiz.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {
    fun save(appUser: AppUser): Long? = userRepository.save(appUser).id

    fun findUserByEmail(email: String) = userRepository.findUserByEmail(email)
}