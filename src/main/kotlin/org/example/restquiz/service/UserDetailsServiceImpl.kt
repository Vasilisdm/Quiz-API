package org.example.restquiz.service

import org.example.restquiz.domain.adapters.AppUserAdapter
import org.example.restquiz.repositories.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(private val userRepository: UserRepository) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findUserByUsername(username) ?: throw UsernameNotFoundException("Not found")

        return AppUserAdapter(user)
    }
}