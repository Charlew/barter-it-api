package barter.barter_it_api.domain.item

import barter.barter_it_api.infrastructure.item.ItemRepository
import org.jetbrains.annotations.NotNull

import java.util.stream.Collectors

class InMemoryItemRepository implements ItemRepository {

    private final Map<String, Item> items = new HashMap<>()

    @Override
    Item save(@NotNull Item item) {
        items.replace(item.id, item)
    }

    @Override
    Item insert(@NotNull Item item) {
        items.put(item.id, item)
    }

    @Override
    Optional<Item> findById(@NotNull String id) {
        return Optional.ofNullable(items.get(id))
    }

    @Override
    Iterable<Item> findAll() {
        return new ArrayList<>(items.values())
    }

    @Override
    Void deleteAll() {
        return items.clear()
    }

    @Override
    Iterable<Item> findByCategory(@NotNull Categories category) {
        return new ArrayList<>(items.values()
            .stream()
            .filter({ item -> item.category == category })
            .collect(Collectors.toList()))
    }

}
