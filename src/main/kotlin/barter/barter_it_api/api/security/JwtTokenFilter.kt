package barter.barter_it_api.api.security

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.validation.ValidationException


class JwtTokenFilter(private val jwtTokenProvider: JwtTokenProvider) : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val token = jwtTokenProvider.resolveToken(request)
        try {
            if (token != null && jwtTokenProvider.validateToken(token)) {
                SecurityContextHolder.getContext().authentication = jwtTokenProvider.getAuthenticationByToken(token)
            }
        } catch (e: ValidationException) {
            SecurityContextHolder.clearContext()
            response.sendError(400, e.message)
            return
        }
        filterChain.doFilter(request, response)
    }
}