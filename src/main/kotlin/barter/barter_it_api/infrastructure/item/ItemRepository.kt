package barter.barter_it_api.infrastructure.item

import barter.barter_it_api.domain.item.Categories
import barter.barter_it_api.domain.item.Item
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.Repository
import java.util.Optional

interface ItemRepository: Repository<Item, String> {

    fun save(item: Item): Item

    fun insert(item: Item): Item

    fun findById(id: String): Optional<Item>

    fun findAll(): MutableIterable<Item>

    @Query("{ 'category' : ?0 }")
    fun findByCategory(category: Categories): MutableIterable<Item>

    fun deleteAll(): Void

}