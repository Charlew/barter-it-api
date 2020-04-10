package barter.barter_it_api.infrastructure

import barter.barter_it_api.domain.item.Categories.*
import barter.barter_it_api.domain.item.Conditions
import barter.barter_it_api.domain.item.Item
import barter.barter_it_api.domain.user.User
import barter.barter_it_api.infrastructure.item.ItemRepository
import barter.barter_it_api.infrastructure.user.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class DataLoader(
        private val itemRepository: ItemRepository,
        private val userRepository: UserRepository,
        private val bCryptPasswordEncoder: BCryptPasswordEncoder
) {

    @PostConstruct
    fun load() {
        itemRepository.deleteAll()
        userRepository.deleteAll()

        sampleUsers()
                .stream()
                .map {
                    userRepository.save(User(
                            email = it.email,
                            password = bCryptPasswordEncoder.encode(it.password)
                    ))
                }
                .forEach { println(it) }

        sampleItems()
                .stream()
                .map {
                    itemRepository.save(Item(
                            name = it.name,
                            description = it.description,
                            category = it.category,
                            count = it.count,
                            mark = it.mark,
                            condition = it.condition))
                }
                .forEach { println(it) }
    }

    private fun sampleUsers(): List<User> {
        return listOf(
                User(email = "tomasz.adamek@example.com", password = "dummy"),
                User(email = "andrzej.golota@example.com", password = "dummy")
        )
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