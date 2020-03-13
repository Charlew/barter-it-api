package barter.barter_it_api.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Item(@Id val id: String? = null,
                val name: String,
                val description: String?,
                val category: Categories,
                val count: Int? = 1)


enum class Categories {
    CARS,
    JEWELRY_AND_WATCHES,
    HOUSEHOLD,
    CLOTHES,
    FURNITURE
}