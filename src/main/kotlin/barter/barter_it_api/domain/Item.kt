package barter.barter_it_api.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
data class Item(@Id val id: String? = null,
                val name: String,
                val description: String?,
                val category: Categories,
                val createdAt: LocalDateTime = LocalDateTime.now(),
                val count: Int? = 1)


enum class Categories {
    AUTOMOTIVE,
    JEWELRY_AND_WATCHES,
    HOUSEHOLD,
    CLOTHES,
    FURNITURE
}

data class ItemRequest(val name: String,
                       val description: String?,
                       val category: Categories,
                       val count: Int? = 1) {

    fun toItem() = Item(
            name = name,
            description = description,
            category = category,
            count = count
    )
}
