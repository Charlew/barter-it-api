package barter.barter_it_api.api.user

import barter.barter_it_api.api.IntegrationSpec
import barter.barter_it_api.domain.user.AuthService
import org.springframework.beans.factory.annotation.Autowired

import static barter.barter_it_api.Fixtures.*
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class UserIT extends IntegrationSpec {

    @Autowired
    AuthService authService

    def 'should not allow access to unauthenticated users'() {
        expect:
            mvc.perform(get("/items"))
                    .andExpect(status().isForbidden())
    }

    def 'should perform login request and authenticate user'() {
        given:
            authService.register(authRequest("email@example.com", "password123"))

        and:
            def userLoginRequest = mapToJson(authRequest('email@example.com', 'password123'))

        when:
           def response = mvc.perform(post("/login")
                    .content(userLoginRequest)
                    .accept(APPLICATION_JSON)
                    .contentType(APPLICATION_JSON)
            ).andReturn().response

        then: 'expecting token'
            response.status == 200
            response != null
    }

    def 'should not authenticate non existing user'() {
        given:
            def userLoginRequest = mapToJson(authRequest('test', 'test'))

        when:
            def response = mvc.perform(post("/login")
                    .content(userLoginRequest)
                    .accept(APPLICATION_JSON)
                    .contentType(APPLICATION_JSON)
            ).andReturn().response

        then:
            response.status == 400
            response.contentAsString == '{"codes":["Bad credentials"]}'
    }

    def 'should register new user'() {
        given:
            def userRegistrationRequest = mapToJson(authRequest('newUser', 'newPassword'))

        when:
            def response = mvc.perform(post("/register")
                    .content(userRegistrationRequest)
                    .accept(APPLICATION_JSON)
                    .contentType(APPLICATION_JSON)
            ).andReturn().response

        then:
            response.status == 200
            response.contentAsString == 'User: newUser registered'
    }

    def 'should not perform user registration when user has been registered'() {
        given:
            authService.register(authRequest("email@example.com", "password123"))

        and:
            def userRegistrationRequest = mapToJson(authRequest('email@example.com', 'password123'))

        when:
            def response = mvc.perform(post("/register")
                    .content(userRegistrationRequest)
                    .accept(APPLICATION_JSON)
                    .contentType(APPLICATION_JSON)
            ).andReturn().response

        then:
            response.status == 400
            response.contentAsString == '{"codes":["User: email@example.com already exists"]}'
    }

}
