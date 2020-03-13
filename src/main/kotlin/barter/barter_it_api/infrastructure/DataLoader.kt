package barter.barter_it_api.infrastructure

import barter.barter_it_api.domain.Categories.*
import barter.barter_it_api.domain.Item
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class DataLoader(private val repository: ItemRepository) {

    @PostConstruct
    fun load() {
        repository.deleteAll()

        sampleItems()
                .stream()
                .map { repository.save(Item(name = it.name, description = it.description, category = it.category, count = it.count)) }
                .forEach { println(it) }
    }

    private fun sampleItems(): List<Item> {
        return listOf(
                Item(name = "Audi", description = "Super auto", category = CARS, count = 1),
                Item(name = "Pralka", description = "Niezawodna", category = HOUSEHOLD, count = 2),
                Item(name = "Krzesło", description = "Solidne", category = FURNITURE, count = 4),
                Item(name = "Zegarek", description = "Elegancki", category = JEWELRY_AND_WATCHES, count = 3)
        )
    }
}