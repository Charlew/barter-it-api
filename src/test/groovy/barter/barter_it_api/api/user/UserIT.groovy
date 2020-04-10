package barter.barter_it_api.api.user

import barter.barter_it_api.api.IntegrationSpec

import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class UserIT extends IntegrationSpec {

    def 'should not allow access to unauthenticated users'() {
        expect:
            mvc.perform(get("/items"))
                    .andExpect(status().isForbidden())
    }

    def "should perform login request and authenticate user"() {
        given:
            def userLoginRequest = """
                                    {
                                        "email": "tomasz.adamek@example.com",
                                        "password": "dummy"
                                    }
                                    """
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
}
