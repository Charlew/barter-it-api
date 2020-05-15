package barter.barter_it_api.infrastructure

import barter.barter_it_api.domain.item.Categories.*
import barter.barter_it_api.domain.item.Conditions
import barter.barter_it_api.domain.item.Item
import barter.barter_it_api.domain.user.User
import barter.barter_it_api.infrastructure.item.ItemRepository
import barter.barter_it_api.infrastructure.user.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import java.util.*
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
                            encodedPassword = bCryptPasswordEncoder.encode(it.encodedPassword)
                    ))
                }
                .forEach { println(it) }

        sampleItems()
                .stream()
                .map {
                    itemRepository.save(Item(
                            name = it.name,
                            userId = it.userId,
                            description = it.description,
                            category = it.category,
                            count = it.count,
                            mark = it.mark,
                            condition = it.condition,
                            imageIds = it.imageIds,
                            proposals = it.proposals,
                            idsOfItemsProposedInOtherItems = it.idsOfItemsProposedInOtherItems
                    )
                    )
                }
                .forEach { println(it) }
    }

    private fun sampleUsers(): List<User> {
        return listOf(
                User(email = "tomasz.adamek@example.com", encodedPassword = "dummy"),
                User(email = "andrzej.golota@example.com", encodedPassword = "dummy")
        )
    }

    private fun sampleItems(): List<Item> {
        return listOf(
                Item(
                        name = "Audi",
                        userId = UUID.randomUUID().toString(),
                        description = "Super auto",
                        category = AUTOMOTIVE,
                        count = 1, mark = "Audi",
                        condition = Conditions.GOOD,
                        imageIds = null,
                        proposals = null,
                        idsOfItemsProposedInOtherItems = null
                ),
                Item(
                        name = "Pralka",
                        userId = UUID.randomUUID().toString(),
                        description = "Niezawodna",
                        category = HOUSEHOLD,
                        count = 2,
                        mark = "Beko",
                        condition = Conditions.DAMAGED,
                        imageIds = null,
                        proposals = null,
                        idsOfItemsProposedInOtherItems = null
                ),
                Item(
                        name = "Krzesło",
                        userId = UUID.randomUUID().toString(),
                        description = "Solidne",
                        category = FURNITURE,
                        count = 4,
                        mark = "Ikea",
                        condition = Conditions.VERY_GOOD,
                        imageIds = null,
                        proposals = null,
                        idsOfItemsProposedInOtherItems = null
                ),
                Item(
                        name = "Zegarek",
                        userId = UUID.randomUUID().toString(),
                        description = "Elegancki",
                        category = JEWELRY_AND_WATCHES,
                        count = 3,
                        mark = "Rolex",
                        condition = Conditions.GOOD,
                        imageIds = null,
                        proposals = null,
                        idsOfItemsProposedInOtherItems = null
                )
        )
    }
}