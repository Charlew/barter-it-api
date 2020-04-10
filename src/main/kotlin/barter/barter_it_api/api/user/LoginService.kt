package barter.barter_it_api.api.user

import barter.barter_it_api.api.security.JwtTokenProvider
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.stereotype.Service
import javax.validation.ValidationException

@Service
class LoginService(
        private val jwtTokenProvider: JwtTokenProvider,
        private val authenticationManager: AuthenticationManager
) {
    fun login(email: String, password: String): String? {
        return try {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(email, password))
            jwtTokenProvider.createToken(email, password)
        } catch (ex: AuthenticationException) {
            throw ValidationException("Bad credentials")
        }
    }
}