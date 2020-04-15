package barter.barter_it_api.api.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
class CorsFilter(
        @Value("\${security.cors.allowedOrigins:*}")
        private val allowedOrigins: String
) {

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource? {
        val configuration = CorsConfiguration()
        val source = UrlBasedCorsConfigurationSource()
        configuration.allowedOrigins = listOf(*allowedOrigins.split(",".toRegex()).toTypedArray())
        configuration.allowedMethods = listOf("GET", "POST")
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}