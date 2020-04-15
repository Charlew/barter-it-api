package barter.barter_it_api.api.user

import barter.barter_it_api.api.Validations
import barter.barter_it_api.domain.user.AuthService
import barter.barter_it_api.domain.user.UserAuthRequest
import barter.barter_it_api.domain.user.UserLoginResponse
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@Validated
@RestController
class AuthEndpoint(
        private val authService: AuthService,
        private val validations: Validations
) {
    @PostMapping("login")
    fun login(@RequestBody userAuthRequest: UserAuthRequest): UserLoginResponse {
        validations.validate(userAuthRequest)

        return authService.login(userAuthRequest.email, userAuthRequest.password)
    }

    @PostMapping("register")
    fun register(@RequestBody userAuthRequest: UserAuthRequest): String? {
        validations.validate(userAuthRequest)

        return authService.register(userAuthRequest)
    }
}
