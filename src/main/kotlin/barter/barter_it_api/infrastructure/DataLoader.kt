package barter.barter_it_api.infrastructure

import barter.barter_it_api.domain.Categories.*
import barter.barter_it_api.domain.Conditions
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
                .map {
                    repository.save(Item(
                            name = it.name,
                            description = it.description,
                            category = it.category,
                            count = it.count,
                            mark = it.mark,
                            condition = it.condition))
                }
                .forEach { println(it) }
    }

    private fun sampleItems(): List<Item> {
        return listOf(
                Item(name = "Audi", description = "Super auto", category = AUTOMOTIVE, count = 1, mark = "Audi", condition = Conditions.GOOD),
                Item(name = "Pralka", description = "Niezawodna", category = HOUSEHOLD, count = 2, mark = "Beko", condition = Conditions.DAMAGED),
                Item(name = "Krzesło", description = "Solidne", category = FURNITURE, count = 4, mark = "Ikea", condition = Conditions.VERY_GOOD),
                Item(name = "Zegarek", description = "Elegancki", category = JEWELRY_AND_WATCHES, count = 3, mark = "Rolex", condition = Conditions.GOOD)
        )
    }
}