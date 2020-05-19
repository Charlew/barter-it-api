package barter.barter_it_api.api

import barter.barter_it_api.BarterItApiApplication
import barter.barter_it_api.infrastructure.item.ItemRepository
import barter.barter_it_api.infrastructure.user.UserRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import org.springframework.test.context.ContextConfiguration

@ContextConfiguration
@SpringBootTest(
    classes = BarterItApiApplication,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
abstract class IntegrationSpec extends Specification {

    @Autowired
    ObjectMapper objectMapper

    @Autowired
    UserRepository userRepository

    @Autowired
    ItemRepository itemRepository

    @Autowired
    TestRestTemplate http

    @LocalServerPort
    int port

    def setup() {
        cleanUpRepositories()
    }

    def cleanUpRepositories() {
        userRepository.deleteAll()
        itemRepository.deleteAll()
    }

    String mapToJson(Object object) {
        return objectMapper.writeValueAsString(object)
    }

    protected <T> HttpEntity<T> httpRequest(T body = null, String token = null) {
        body == null ?: mapToJson(body)
        def headers = buildHeaders(token)
        return new HttpEntity<>(body, headers)
    }

    HttpHeaders buildHeaders(String token) {
        def httpHeaders = new HttpHeaders()
        if (token != null) {
            httpHeaders.setBearerAuth(token)
        }
        httpHeaders.set("Content-type", "application/json")
        return httpHeaders
    }

    String url(String endpoint) {
        return "http://localhost:$port/$endpoint"
    }
}
