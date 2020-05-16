package barter.barter_it_api

import barter.barter_it_api.domain.item.Categories
import barter.barter_it_api.domain.item.Conditions
import barter.barter_it_api.domain.item.Item
import barter.barter_it_api.domain.item.ItemRequest
import barter.barter_it_api.domain.item.Proposal
import barter.barter_it_api.domain.item.Status
import barter.barter_it_api.domain.user.UserAuthRequest

import static barter.barter_it_api.domain.item.Categories.AUTOMOTIVE

class Fixtures {

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

    static UserAuthRequest authRequest(String email, String password) {
        return new UserAuthRequest(email, password)
    }

    static ItemRequest itemRequest(Categories category = AUTOMOTIVE) {
        return new ItemRequest("Audi - wymienie",
            null,
            "Igla",
            category,
            Conditions.GOOD,
            "Audi",
            1, null, null, null
        )
    }

}
