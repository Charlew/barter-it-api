package barter.barter_it_api.domain.item

import barter.barter_it_api.infrastructure.item.ItemRepository
import spock.lang.Specification
import spock.lang.Subject

import static barter.barter_it_api.domain.item.Categories.*

class ItemFacadeSpec extends Specification {

    @Subject
    ItemFacade facade

    ItemRepository repository

    def setup() {
        repository = new InMemoryItemRepository()
        facade = new ItemFacade(repository)
    }

    def 'should correctly update status of item'() {
        given:
            repository.insert(sampleItem())
        when:
            def result = facade.updateStatusOfProposal('test-id1', 'test-id2', status)
        then:
            result.proposals[0].status == status
        where:
            status << [Status.ACCEPTED, Status.REJECTED, Status.PENDING]
    }

    def 'should get items by category'() {
        given:
            repository.insert(sampleItem("id1", AUTOMOTIVE))
            repository.insert(sampleItem("id2", AUTOMOTIVE))
            repository.insert(sampleItem("id3", JEWELRY_AND_WATCHES))

        when:
            def result = facade.getItems("automotive")

        then:
            result.size() == 2

        and:
            result[0].category == AUTOMOTIVE
            result[1].category == AUTOMOTIVE
    }

    def 'should throw IllegalArgumentException when trying to get items from not existing category'() {
        when:
            facade.getItems("cars")

        then:
            def exception = thrown(IllegalArgumentException)

        and:
            exception.message == "No predefined constant value for given category: CARS"
    }

    static Item sampleItem(String id = "test-id1", Categories categories = AUTOMOTIVE) {
        new Item(
                id,
                'test-user-id',
                'auto',
                'super',
                categories,
                Conditions.DAMAGED,
                'Audi',
                null,
                1,
                null,
                proposals(),
                null
        )
    }

    static def itemProposed() {
        new Item(
                'test-id2',
                'test-user-id',
                'auto',
                'extra',
                AUTOMOTIVE,
                Conditions.DAMAGED,
                'BMW',
                null,
                1,
                null,
                null,
                null
        )
    }

    static def proposals() {
        return List.of(new Proposal(Status.PENDING, itemProposed()))
    }
}
