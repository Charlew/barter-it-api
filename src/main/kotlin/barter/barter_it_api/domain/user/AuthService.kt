package barter.barter_it_api.domain.user

import barter.barter_it_api.api.ValidationException
import barter.barter_it_api.api.security.JwtTokenProvider
import barter.barter_it_api.infrastructure.user.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

@Service
class AuthService(
        private val jwtTokenProvider: JwtTokenProvider,
        private val authenticationManager: AuthenticationManager,
        private val userRepository: UserRepository,
        private val bCryptPasswordEncoder: BCryptPasswordEncoder
) {
    fun login(email: String, password: String): AccessToken {
            return try {
                authenticationManager.authenticate(UsernamePasswordAuthenticationToken(email, password))
                jwtTokenProvider.createToken(email)
            } catch (ex: AuthenticationException) {
                throw ValidationException("Bad credentials")
            }
    }

    fun register(userAuthRequest: UserAuthRequest): String? {
        userAuthRequest.let {
            if (userRepository.existsByEmail(it.email)) {
                throw ValidationException("User: ${it.email} already exists")
            }
            userRepository.save(it.toUser(bCryptPasswordEncoder.encode(userAuthRequest.password)))
            return "User: ${it.email} registered"
        }
    }

    fun refreshToken(email: String?): AccessToken {
        if (email == null) {
            throw ValidationException("User must be authenticated")
        }
        return jwtTokenProvider.createToken(email)
    }

    fun getInfo(request: HttpServletRequest): UserInfoResponse {
        val email = jwtTokenProvider.resolveToken(request).let { jwtTokenProvider.getEmail(it) }
        return userRepository.findByEmail(email)!!.toUserInfo()
    }
}