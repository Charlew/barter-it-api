package barter.barter_it_api.api.item

import barter.barter_it_api.api.IntegrationSpec
import barter.barter_it_api.domain.item.Categories
import barter.barter_it_api.domain.item.Conditions
import barter.barter_it_api.domain.item.Item
import org.springframework.security.test.context.support.WithMockUser

import static barter.barter_it_api.domain.item.Categories.*
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

@WithMockUser
class ItemIT extends IntegrationSpec {

    def 'should create valid item'() {
        given:
            def request = itemRequest()

        when:
            def response = mvc.perform(post("/items/create")
                    .accept(APPLICATION_JSON)
                    .contentType(APPLICATION_JSON)
                    .content(request)
            ).andReturn().response

            def result = objectMapper.readValue(response.getContentAsString(), Item)
        then:
            response.status == 200

            result.name == 'Audi - wymienie'
            result.description == 'Igla'
            result.category == AUTOMOTIVE
            result.count == 1
            result.condition == Conditions.GOOD
            result.mark == "Audi"
    }

    def 'should not get item that does not exist'() {
        given:
            def itemId = UUID.randomUUID().toString()
        when:
            def response = mvc.perform(get("/items/$itemId")
                    .accept(APPLICATION_JSON)
                    .contentType(APPLICATION_JSON)
            ).andReturn().response
        then:
            response.status == 400
            response.contentAsString == '{"codes":["Item does not exist"]}'
    }

    def 'should get items by category'() {
        given:
            def automotiveItem = itemRequest(AUTOMOTIVE)

        and:
            def householdItem = itemRequest(HOUSEHOLD)

        and:
            mvc.perform(post("/items/create")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(automotiveItem)
            ).andReturn().response

        and:
            mvc.perform(post("/items/create")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(householdItem)
            ).andReturn().response

        when:
            def response = mvc.perform(get("/items?category=automotive")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
            ).andReturn().response

        and:
            def automotiveItems = objectMapper.readValue(response.getContentAsString(), Iterable.class)

        then:
            automotiveItems.size() == 1
    }

    private static String itemRequest(Categories category = AUTOMOTIVE) {
        def categoryName = category.name()

        """{
          "name": "Audi - wymienie",
          "description": "Igla",
          "category": "$categoryName",
          "condition": "GOOD",
          "mark": "Audi",
          "count": 1
         }
         """
    }
}
