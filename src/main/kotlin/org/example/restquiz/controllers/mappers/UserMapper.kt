package org.example.restquiz.controllers.mappers

import org.example.restquiz.controllers.dto.RegistrationRequest
import org.example.restquiz.domain.entities.AppUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder

private const val ROLE_USER = "ROLE_USER"

class UserMapper {

    companion object {
        fun mapToUser(registrationRequest: RegistrationRequest, encodedPassword: String) = AppUser(
            username = registrationRequest.email,
            email = registrationRequest.email,
            password = encodedPassword,
            authority = ROLE_USER,
        )
    }
}