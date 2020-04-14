package barter.barter_it_api.infrastructure.user

import barter.barter_it_api.domain.user.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: CrudRepository<User, String> {

    fun findByEmail(email: String): User?

    fun existsByEmail(email: String?): Boolean
}
