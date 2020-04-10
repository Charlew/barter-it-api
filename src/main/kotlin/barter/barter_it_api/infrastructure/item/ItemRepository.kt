package barter.barter_it_api.infrastructure.item

import barter.barter_it_api.domain.item.Item
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ItemRepository:CrudRepository<Item, String>