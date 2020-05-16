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

    HttpEntity<String> httpRequest(Object body) {
        def json = mapToJson(body)
        def httpHeaders = new HttpHeaders()
        httpHeaders.set("Content-type", "application/json")
        return new HttpEntity<>(json, httpHeaders)
    }

    String url(String endpoint) {
        return "http://localhost:$port/$endpoint"
    }
}
