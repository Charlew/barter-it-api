package barter.barter_it_api.domain.user

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import javax.validation.constraints.NotBlank

@Document
data class User(
        @Id
        val id: String? = null,

        @Indexed(unique = true)
        val email: String,

        val password: String
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
        val email: String
)

fun UserAuthRequest.toUser() = User(
        email = this.email,
        password = this.password)

fun User.toUserLoginResponse(token: String) = UserLoginResponse(
        token = token,
        id = this.id,
        email = this.email
)