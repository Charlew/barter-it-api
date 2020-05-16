package barter.barter_it_api.domain.item

import barter.barter_it_api.api.ValidationException
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.*
import javax.validation.constraints.*

@Document
data class Item(@Id val id: String = UUID.randomUUID().toString(),
                val userId: String,
                val name: String,
                val description: String,
                val category: Categories,
                val condition: Conditions,
                val mark: String?,
                val createdAt: LocalDateTime? = LocalDateTime.now(),
                val quantity: Int = 1,
                val imageIds: List<String>?,
                val proposals: MutableList<Proposal>?,
                val idsOfItemsProposedInOtherItems: List<String>?
)

data class ItemRequest(
        @get:NotBlank(message = "notBlank")
        val name: String,

        @get:NotBlank(message = "notBlank")
        val userId: String,

        @get:NotBlank(message = "notBlank")
        val description: String,

        @get:NotNull(message = "required")
        val category: Categories,

        @get:NotNull(message = "required")
        val condition: Conditions,

        val mark: String?,

        @get:Min(value = 1, message = "tooSmall")
        val quantity: Int = 1,

        val imageIds: List<String>?,

        val proposals: MutableList<Proposal>?,

        val proposedInOthersList: List<String>?
)

data class Proposal(
        @get:NotNull(message = "required")
        var status: Status,

        @get:NotNull(message = "required")
        val item: Item
)

enum class Categories {
    AUTOMOTIVE,
    JEWELRY_AND_WATCHES,
    HOUSEHOLD,
    CLOTHES,
    FURNITURE;

    companion object {
        private val nameToValueMap = values().associateBy(Categories::name)
        fun fromName(name: String) = nameToValueMap[name] ?: throw ValidationException("No predefined constant value for given category: $name")
    }

}

enum class Conditions {
    VERY_GOOD,
    GOOD,
    DAMAGED
}

enum class Status {
    PENDING,
    REJECTED,
    ACCEPTED
}

fun ItemRequest.toItem() = Item(
        name = this.name,
        userId = this.userId,
        description = this.description,
        category = this.category,
        condition = this.condition,
        mark = this.mark,
        quantity = this.quantity,
        imageIds = this.imageIds,
        proposals = this.proposals,
        idsOfItemsProposedInOtherItems = this.proposedInOthersList
)
