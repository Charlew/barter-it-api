package barter.barter_it_api.domain.item

import barter.barter_it_api.infrastructure.item.ItemRepository
import org.springframework.stereotype.Service

@Service
class ItemFacade(private val repository: ItemRepository) {

    fun getAllItems(): MutableIterable<Item> = repository.findAll()

    fun getItemById(id: String): Item? = repository.findById(id)
            .orElse(null)

    fun create(itemRequest: ItemRequest): Item = repository.save(itemRequest.toItem())
}
