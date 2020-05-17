package barter.barter_it_api.domain.user

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import javax.validation.constraints.NotBlank

@Document
data class User(
        @Id
        val id: String? = null,

        @Indexed(unique = true)
        val email: String,

        val encodedPassword: String
)

data class UserAuthRequest (
        @get:NotBlank(message = "required")
        val email: String,

        @get:NotBlank(message = "required")
        val password: String
)

data class UserLoginResponse(
        val token: String,
        val id: String?,
        val email: String,
        val tokenExpirationDate: LocalDateTime
)

fun UserAuthRequest.toUser(encodedPassword: String) = User(
        email = this.email,
        encodedPassword = encodedPassword)

fun User.toUserLoginResponse(token: String, tokenExpirationDate: LocalDateTime) = UserLoginResponse(
        token = token,
        tokenExpirationDate = tokenExpirationDate,
        id = this.id,
        email = this.email
)