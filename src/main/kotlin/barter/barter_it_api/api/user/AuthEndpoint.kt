package barter.barter_it_api.api.user

import barter.barter_it_api.api.Validations
import barter.barter_it_api.domain.user.AccessToken
import barter.barter_it_api.domain.user.AuthService
import barter.barter_it_api.domain.user.UserAuthRequest
import barter.barter_it_api.domain.user.UserInfoResponse
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@Validated
@RestController
class AuthEndpoint(
        private val authService: AuthService,
        private val validations: Validations
) {
    @PostMapping("login")
    fun login(@RequestBody userAuthRequest: UserAuthRequest): AccessToken {
        validations.validate(userAuthRequest)

        return authService.login(userAuthRequest.email, userAuthRequest.password)
    }

    @PostMapping("register")
    fun register(@RequestBody userAuthRequest: UserAuthRequest): String? {
        validations.validate(userAuthRequest)

        return authService.register(userAuthRequest)
    }

    @GetMapping("refresh")
    fun refreshToken(request: HttpServletRequest): AccessToken = authService.refreshToken(request.remoteUser)

    @GetMapping("/info")
    fun info(request: HttpServletRequest): UserInfoResponse = authService.getInfo(request)
}
