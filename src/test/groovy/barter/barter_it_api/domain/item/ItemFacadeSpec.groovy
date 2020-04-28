package barter.barter_it_api.domain.item

import barter.barter_it_api.infrastructure.item.ItemRepository
import spock.lang.Specification
import spock.lang.Subject

class ItemFacadeSpec extends Specification {

    @Subject
    ItemFacade facade

    ItemRepository repository = Mock()

    def setup() {
        facade = new ItemFacade(repository)
    }

    def 'should correctly update status of item'() {
        given:
            repository.findById('test-id1') >> Optional.of(sampleItem())
        when:
            def result = facade.updateStatusOfProposal('test-id1', 'test-id2', status)
        then:
            result.proposals[0].status == status
        where:
            status << [Status.ACCEPTED, Status.REJECTED, Status.PENDING]
    }

    static def sampleItem() {
        new Item(
                'test-id1',
                'test-user-id',
                'auto',
                'super',
                Categories.AUTOMOTIVE,
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
                Categories.AUTOMOTIVE,
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
