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

data class UserLoginRequest (
        @get:NotBlank(message = "required")
        val email: String,

        @get:NotBlank(message = "required")
        val password: String
) {
    fun toUser() = User(
            email = email,
            password = password
    )
}

