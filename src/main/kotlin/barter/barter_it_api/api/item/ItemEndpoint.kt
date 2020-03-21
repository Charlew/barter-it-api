package barter.barter_it_api.api.item

import barter.barter_it_api.domain.Item
import barter.barter_it_api.domain.ItemFacade
import barter.barter_it_api.domain.ItemRequest
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/items")
class ItemEndpoint(private val facade: ItemFacade) {

    @GetMapping(produces = [APPLICATION_JSON_VALUE])
    fun items(): MutableIterable<Item> = facade.getAllItems()

    @GetMapping("/{id}", produces = [APPLICATION_JSON_VALUE])
    fun byId(@PathVariable(name = "id") id: String): Item? = facade.getItemById(id)

    @PostMapping(produces = [APPLICATION_JSON_VALUE], consumes = [APPLICATION_JSON_VALUE])
    fun create(@RequestBody(required = true) itemRequest: ItemRequest): Item = facade.create(itemRequest)
}