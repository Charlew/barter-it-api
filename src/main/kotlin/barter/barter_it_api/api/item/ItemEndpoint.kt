package barter.barter_it_api.api.item

import barter.barter_it_api.domain.Item
import barter.barter_it_api.domain.ItemFacade
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/items")
class ItemEndpoint(private val facade: ItemFacade) {

    @GetMapping
    fun items(): MutableIterable<Item> {
        return facade.getAllItems()
    }

    @GetMapping("/{id}")
    fun byId(@PathVariable(name = "id") id: String): Item? {
        return facade.getItemById(id)
    }
}