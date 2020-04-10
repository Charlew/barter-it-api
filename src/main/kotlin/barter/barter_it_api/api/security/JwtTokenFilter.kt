package barter.barter_it_api.api.security

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtTokenFilter(private val jwtTokenProvider: JwtTokenProvider) : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        jwtTokenProvider.resolveToken(request).let {
            when {
                it != null && jwtTokenProvider.validateToken(it) -> {
                    SecurityContextHolder.getContext().authentication = jwtTokenProvider.getAuthenticationByToken(it)
                }
            }
        }
        filterChain.doFilter(request, response)
    }
}