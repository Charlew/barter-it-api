package barter.barter_it_api.api.security

import org.apache.shiro.realm.Realm
import org.apache.shiro.realm.text.IniRealm
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ShiroConfig {

    @Bean
    fun realm(@Value("\${apiUsers.path}") filePath: String?): Realm? = IniRealm(filePath)

    @Bean
    fun shiroFilterChainDefinition(): ShiroFilterChainDefinition? {
        return DefaultShiroFilterChainDefinition().apply {
            addPathDefinition("/**", "authcBasic")
        }
    }

}