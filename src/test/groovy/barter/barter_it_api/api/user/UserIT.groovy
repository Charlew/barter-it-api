package barter.barter_it_api.api.user

import barter.barter_it_api.api.IntegrationSpec
import org.junit.Ignore

import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@Ignore("until testcontainers is repaired")
class UserIT extends IntegrationSpec {

    def 'should not allow access to unauthenticated users'() {
        expect:
            mvc.perform(get("/items"))
                    .andExpect(status().isForbidden())
    }

    def 'should perform login request and authenticate user'() {
        given:
            def userLoginRequest = authRequest('tomasz.adamek@example.com', 'dummy')
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
            def userLoginRequest = authRequest('test', 'test')
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
            def userRegistrationRequest = authRequest('newUser', 'newPassword')
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
            def userRegistrationRequest = authRequest('tomasz.adamek@example.com', 'password')
        when:
            def response = mvc.perform(post("/register")
                    .content(userRegistrationRequest)
                    .accept(APPLICATION_JSON)
                    .contentType(APPLICATION_JSON)
            ).andReturn().response
        then:
            response.status == 400
            response.contentAsString == '{"codes":["User: tomasz.adamek@example.com already exists"]}'
    }

    def authRequest(String email, String password) {
        return """
                    {
                        "email": "$email",
                        "password": "$password"
                    }
                """
    }
}
