package barter.barter_it_api.domain.item

import barter.barter_it_api.api.ValidationException
import barter.barter_it_api.infrastructure.item.ItemRepository
import org.springframework.stereotype.Service

@Service
class ItemFacade(private val repository: ItemRepository) {

    fun getItems(category: String?): MutableIterable<Item> {
        if (category != null) {
            return repository.findByCategory(Categories.fromName(category.toUpperCase()))
        }

        return repository.findAll()
    }

    fun getItemById(id: String): Item = findItemById(id)

    fun create(itemRequest: ItemRequest): Item = repository.insert(itemRequest.toItem())

    fun update(itemRequest: ItemRequest): Item = repository.save(itemRequest.toItem())

    fun updateStatusOfProposal(itemId: String, proposalId: String, status: Status): Item {
        val item = findItemById(itemId)

        if (item.proposals != null) {
            updateProposalStatus(item.proposals, proposalId, status)
        }
        if (item.idsOfItemsProposedInOtherItems != null && status == Status.ACCEPTED) {
            updateStatusOfItemProposedInOthersList(item.idsOfItemsProposedInOtherItems)
        }
        repository.save(item)
        return item
    }

    private fun findItemById(id: String): Item = repository.findById(id)
            .orElseThrow { ValidationException("Item does not exist") }

    private fun updateProposalStatus(proposals: List<Proposal>, proposalId: String, status: Status) {
        proposals
                .filter { it.item.id == proposalId }
                .forEach { it.status = status }
    }

    private fun updateStatusOfItemProposedInOthersList(proposedInOthersList: List<String>) {
        proposedInOthersList
                .forEach {
                    val proposedItem = repository.findById(it).get()
                    proposedItem.proposals!!
                            .forEach { proposal -> proposal.status = Status.REJECTED }
                }
    }
}
