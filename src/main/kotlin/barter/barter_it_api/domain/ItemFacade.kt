package barter.barter_it_api.domain

import barter.barter_it_api.infrastructure.ItemRepository
import org.springframework.stereotype.Service

@Service
class ItemFacade(private val repository: ItemRepository) {

    fun getAllItems(): MutableIterable<Item> = repository.findAll()

    fun getItemById(id: String): Item? = repository.findById(id)
            .orElse(null)
}
