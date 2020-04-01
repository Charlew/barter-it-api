package barter.barter_it_api.api.item

import barter.barter_it_api.api.IntegrationSpec
import barter.barter_it_api.domain.Categories
import barter.barter_it_api.domain.Conditions
import barter.barter_it_api.domain.Item

import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

class ItemIT extends IntegrationSpec {

    def 'should create valid item'() {
        given:
            def request = """{
                              "name": "Audi - wymienie",
                              "description": "Igla",
                              "category": "AUTOMOTIVE",
                              "condition": "GOOD",
                              "mark": "Audi",
                              "count": 1
                             }
                             """

        when:
            def response = mvc.perform(post("/items")
                    .accept(APPLICATION_JSON)
                    .contentType(APPLICATION_JSON)
                    .content(request)
            ).andReturn().response

            def result = objectMapper.readValue(response.getContentAsString(), Item)
        then:
            response.status == 200

            result.name == 'Audi - wymienie'
            result.description == 'Igla'
            result.category == Categories.AUTOMOTIVE
            result.count == 1
            result.condition == Conditions.GOOD
            result.mark == "Audi"
    }
}
