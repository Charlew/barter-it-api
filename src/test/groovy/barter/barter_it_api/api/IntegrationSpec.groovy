package barter.barter_it_api.api

import barter.barter_it_api.BarterItApiApplication
import barter.barter_it_api.infrastructure.item.ItemRepository
import barter.barter_it_api.infrastructure.user.UserRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import org.springframework.test.context.ContextConfiguration

@ContextConfiguration
@AutoConfigureMockMvc
@SpringBootTest
@Import(BarterItApiApplication)
@ActiveProfiles("test")
abstract class IntegrationSpec extends Specification {

    @Autowired
    protected MockMvc mvc

    @Autowired
    ObjectMapper objectMapper

    @Autowired
    UserRepository userRepository

    @Autowired
    ItemRepository itemRepository

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

}
