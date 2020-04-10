package barter.barter_it_api.api.user

import barter.barter_it_api.api.Validations
import barter.barter_it_api.domain.user.UserLoginRequest
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@Validated
@RestController
class LoginEndpoint(
        private val loginService: LoginService,
        private val validations: Validations
) {

    @PostMapping("login")
    fun login(@RequestBody userLoginRequest: UserLoginRequest): String? {
        validations.validate(userLoginRequest)

        return loginService.login(userLoginRequest.email, userLoginRequest.password)
    }
}
