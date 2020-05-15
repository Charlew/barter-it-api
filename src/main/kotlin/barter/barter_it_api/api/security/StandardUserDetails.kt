package barter.barter_it_api.api.security

import barter.barter_it_api.domain.user.User
import barter.barter_it_api.infrastructure.user.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class StandardUserDetails(
        private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        val user: User = userRepository.findByEmail(email) ?: throw UsernameNotFoundException("User: $email not found")

        return org.springframework.security.core.userdetails.User
                .withUsername(email)
                .password(user.encodedPassword)
                .authorities("user")
                .build()
    }
}