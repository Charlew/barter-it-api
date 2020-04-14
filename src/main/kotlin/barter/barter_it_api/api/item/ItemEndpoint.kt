package barter.barter_it_api.api.item

import barter.barter_it_api.api.Validations
import barter.barter_it_api.domain.item.Item
import barter.barter_it_api.domain.item.ItemFacade
import barter.barter_it_api.domain.item.ItemRequest
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Validated
@RestController
@CrossOrigin("*")
@RequestMapping("/items")
class ItemEndpoint(private val facade: ItemFacade,
                   private val validations: Validations) {

    @GetMapping(produces = [APPLICATION_JSON_VALUE])
    fun items(): MutableIterable<Item> = facade.getAllItems()

    @GetMapping("/{id}", produces = [APPLICATION_JSON_VALUE])
    fun byId(@PathVariable(name = "id") id: String): Item? = facade.getItemById(id)

    @PostMapping(produces = [APPLICATION_JSON_VALUE], consumes = [APPLICATION_JSON_VALUE])
    fun create(@RequestBody(required = true) itemRequest: ItemRequest): Item {
        validations.validate(itemRequest)

        return facade.create(itemRequest)
    }
}