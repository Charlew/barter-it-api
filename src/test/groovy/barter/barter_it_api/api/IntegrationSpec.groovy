package barter.barter_it_api.api

import barter.barter_it_api.BarterItApiApplication
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import org.springframework.test.context.ContextConfiguration

@ContextConfiguration
@AutoConfigureMockMvc
@SpringBootTest
@Import(BarterItApiApplication)
@TestPropertySource(value = "classpath:application-test.properties")
abstract class IntegrationSpec extends Specification {

    @Autowired
    protected MockMvc mvc

    @Autowired
    ObjectMapper objectMapper

    @Value('${username}')
    protected String username

    @Value('${password}')
    protected String password
}
