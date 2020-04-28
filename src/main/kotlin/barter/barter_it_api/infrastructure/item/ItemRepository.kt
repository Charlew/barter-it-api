package barter.barter_it_api.infrastructure.item

import barter.barter_it_api.domain.item.Item
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ItemRepository:MongoRepository<Item, String> {

    override fun findById(id: String): Optional<Item>
}