package barter.barter_it_api.api.user

import barter.barter_it_api.api.IntegrationSpec
import barter.barter_it_api.api.Problem
import barter.barter_it_api.domain.user.AuthService
import barter.barter_it_api.domain.user.UserLoginResponse
import org.springframework.beans.factory.annotation.Autowired

import static barter.barter_it_api.Fixtures.*
import static org.springframework.http.HttpStatus.*

class UserIT extends IntegrationSpec {

    @Autowired
    AuthService authService

    def 'should perform login request and authenticate user'() {
        given:
            authService.register(authRequest("email@example.com", "password123"))

        and:
            def userLoginRequest = httpRequest(authRequest('email@example.com', 'password123'))

        when:
            def response = http.postForEntity(url("login"), userLoginRequest, UserLoginResponse.class)

        then: 'expecting token'
            response.statusCode == OK
            response.getBody().getTokenExpirationDate()
            response.getBody().getEmail() == "email@example.com"
    }

    def 'should not authenticate non existing user'() {
        given:
            def userLoginRequest = httpRequest(authRequest('test', 'test'))

        when:
            def response = http.postForEntity(url("login"), userLoginRequest, Problem.class)

        then:
            response.statusCode == BAD_REQUEST

        and:
            response.getBody().getCodes().contains("Bad credentials")
    }

    def 'should register new user'() {
        given:
            def userRegistrationRequest = httpRequest(authRequest('newUser', 'newPassword'))

        when:
            def response = http.postForEntity(url("register"), userRegistrationRequest, String.class)

        then:
            response.statusCode == OK
            response.body == 'User: newUser registered'
    }

    def 'should not perform user registration when user has been registered'() {
        given:
            authService.register(authRequest("email@example.com", "password123"))

        and:
            def userRegistrationRequest = httpRequest(authRequest('email@example.com', 'password123'))

        when:
            def response = http.postForEntity(url("register"), userRegistrationRequest, Problem.class)

        then:
            response.statusCode == BAD_REQUEST

        and:
            response.getBody().getCodes().contains("User: email@example.com already exists")
    }

    def 'should refresh authentication token'() {
        given:
            authService.register(authRequest('email@example.com', 'password123'))
            def userLoginRequest = httpRequest(authRequest('email@example.com', 'password123'))
        when:
            def response = http.postForEntity(url('login'), userLoginRequest, UserLoginResponse.class)
            def token = response.body.token
        and:
            def freshToken = http.postForEntity(url('refresh'), null, String.class).body
        then:
            token instanceof String
            freshToken instanceof String
        and:
            token != freshToken
    }

}
