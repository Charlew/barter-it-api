package barter.barter_it_api.api.item

import barter.barter_it_api.api.Validations
import barter.barter_it_api.api.security.JwtTokenProvider
import barter.barter_it_api.domain.item.ItemFacade
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(ItemEndpoint.class)
class ItemEndpointSpec extends Specification {

    @Autowired
    MockMvc mockMvc

    @MockBean
    ItemFacade itemFacade

    @MockBean
    Validations validations

    @MockBean
    JwtTokenProvider jwtTokenProvider

    def "should get all categories"() {
        expect:
            mockMvc.perform(get("/items/categories")
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string("""["AUTOMOTIVE","JEWELRY_AND_WATCHES","HOUSEHOLD","CLOTHES","FURNITURE"]"""))
    }

}
