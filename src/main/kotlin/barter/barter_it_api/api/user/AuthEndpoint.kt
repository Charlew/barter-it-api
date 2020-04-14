package barter.barter_it_api.api.user

import barter.barter_it_api.api.Validations
import barter.barter_it_api.domain.user.AuthService
import barter.barter_it_api.domain.user.UserAuthRequest
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@Validated
@CrossOrigin(origins = ["http://localhost:3000", "http://barter-it-web.herokuapp.com"])
@RestController
class AuthEndpoint(
        private val authService: AuthService,
        private val validations: Validations
) {
    @PostMapping("login")
    fun login(@RequestBody userAuthRequest: UserAuthRequest): String? {
        validations.validate(userAuthRequest)

        return authService.login(userAuthRequest.email, userAuthRequest.password)
    }

    @PostMapping("register")
    fun register(@RequestBody userAuthRequest: UserAuthRequest): String? {
        validations.validate(userAuthRequest)

        return authService.register(userAuthRequest)
    }
}
