package barter.barter_it_api.api.security

import barter.barter_it_api.localClock
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*
import javax.annotation.PostConstruct
import javax.servlet.http.HttpServletRequest
import javax.validation.ValidationException

@Component
class JwtTokenProvider(
        @Value("\${security.token.secret-key}") var secretKey: String,
        @Value("\${security.token.validity}") val validityInMilliseconds: Long,
        private val userDetails: StandardUserDetails)
{

    @PostConstruct
    fun init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.toByteArray())
    }

    fun createToken(email: String, password: String): String? {
        val now = Date.from(Instant.now(localClock()))

        return Jwts.builder()
                .setSubject(email)
                .claim("roles", "user")
                .setIssuedAt(now)
                .setExpiration(Date(now.time + validityInMilliseconds))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact()
    }

    fun resolveToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        return if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7)
        } else null
    }

    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
            true
        } catch (e: JwtException) {
            throw ValidationException("Expired or invalid JWT token")
        } catch (e: IllegalArgumentException) {
            throw RuntimeException(e)
        }
    }

    fun getEmail(token: String?): String {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .body
                .subject
    }

    fun getAuthenticationByToken(token: String): UsernamePasswordAuthenticationToken {
        val userDetails = userDetails.loadUserByUsername(getEmail(token))

        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }
}