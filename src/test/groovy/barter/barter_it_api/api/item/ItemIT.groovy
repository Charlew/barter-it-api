package barter.barter_it_api.api.item

import barter.barter_it_api.api.IntegrationSpec
import barter.barter_it_api.api.Problem
import barter.barter_it_api.domain.item.Conditions
import barter.barter_it_api.domain.item.Item
import barter.barter_it_api.domain.item.ItemFacade
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.test.context.support.WithMockUser

import static barter.barter_it_api.Fixtures.*
import static barter.barter_it_api.domain.item.Categories.*
import static org.springframework.http.HttpStatus.*

@WithMockUser
class ItemIT extends IntegrationSpec {

    @Autowired
    ItemFacade itemFacade

    def 'should create valid item'() {
        given:
            def itemRequest = httpRequest(itemRequest())

        when:
            def response = http.postForEntity(url("items/create"), itemRequest, Item.class)

        then:
            response.statusCode == OK

        and:
            response.getBody().name == 'Audi - wymienie'
            response.getBody().description == 'Igla'
            response.getBody().category == AUTOMOTIVE
            response.getBody().count == 1
            response.getBody().condition == Conditions.GOOD
            response.getBody().mark == "Audi"
    }

    def 'should not get item that does not exist'() {
        given:
            def itemId = UUID.randomUUID().toString()

        when:
            def response = http.getForEntity(url("items/$itemId"), Problem.class)

        then:
            response.statusCode == BAD_REQUEST

        and:
            response.body.getCodes().contains("Item does not exist")
    }

    def 'should get items by category'() {
        given:
            itemFacade.create(itemRequest(AUTOMOTIVE))

        and:
            itemFacade.create(itemRequest(HOUSEHOLD))

        when:
            def response = http.getForEntity(url("items?category=automotive"), Item[].class)

        then:
            response.statusCode == OK

        and:
            response.getBody().size() == 1
            response.getBody()[0].category == AUTOMOTIVE
    }

}
