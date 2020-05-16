package barter.barter_it_api.api.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.BeanIds
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebSecurityConfig(
        private val jwtTokenProvider: JwtTokenProvider,
        @Value("\${security.cors.allowedOrigins:*}")
        private val allowedOrigins: String
 ) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http
                .cors()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/login/**", "/items/**").permitAll()
                .antMatchers("/image/**", "/items/create", "/items/update").authenticated()

        http.apply(JwtTokenFilterApplier(jwtTokenProvider))
    }

    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers("/items/{id}")
    }

    @Bean(name = [BeanIds.AUTHENTICATION_MANAGER])
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager? {
        return super.authenticationManagerBean()
    }

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun corsConfigurer(): WebMvcConfigurer? {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/**").allowedOrigins(*allowedOrigins.split(",".toRegex()).toTypedArray())
            }
        }
    }
}

class JwtTokenFilterApplier(private val jwtTokenProvider: JwtTokenProvider)
    : SecurityConfigurerAdapter<DefaultSecurityFilterChain?, HttpSecurity>() {

    override fun configure(http: HttpSecurity) {
        http.addFilterBefore(JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter::class.java)
    }
}