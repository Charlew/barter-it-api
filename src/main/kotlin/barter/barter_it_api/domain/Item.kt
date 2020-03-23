package barter.barter_it_api.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import javax.validation.constraints.*

@Document
data class Item(@Id val id: String? = null,
                val name: String?,
                val description: String?,
                val category: Categories?,
                val condition: Conditions?,
                val mark: String?,
                val createdAt: LocalDateTime = LocalDateTime.now(),
                val count: Int? = 1)


enum class Categories {
    AUTOMOTIVE,
    JEWELRY_AND_WATCHES,
    HOUSEHOLD,
    CLOTHES,
    FURNITURE
}

enum class Conditions {
    VERY_GOOD,
    GOOD,
    DAMAGED
}

data class ItemRequest(
        @get:NotBlank(message = "notBlank")
        val name: String?,

        val description: String?,

        @get:NotNull(message = "required")
        val category: Categories?,

        @get:NotNull(message = "required")
        val condition: Conditions?,

        val mark: String?,

        @get:Min(value = 1, message = "tooSmall")
        val count: Int? = 1) {

    fun toItem() = Item(
            name = name,
            description = description,
            category = category,
            condition = condition,
            mark = mark,
            count = count
    )
}
