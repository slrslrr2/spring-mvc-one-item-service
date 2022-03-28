package hello.itemservice.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemRepositoryTest {
    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void storeClear(){
        itemRepository.clearStore();
    }

    @Test
    void save(){
        // given
        Item item = new Item("banana", 2000, 1);

        // when
        Item saveItem = itemRepository.save(item);

        // then
        Assertions.assertThat(item).isEqualTo(saveItem);
    }

    @Test
    void finaAll(){
        // given
        Item item = new Item("banana", 2000, 1);
        Item item2 = new Item("apple", 3000, 2);

        itemRepository.save(item);
        itemRepository.save(item2);

        // when
        List<Item> list = itemRepository.findAll();

        // then
        Assertions.assertThat(list.size()).isEqualTo(2);
        Assertions.assertThat(list).contains(item);
        Assertions.assertThat(list).contains(item2);
    }

    @Test
    void update(){
        // given
        Item item = new Item("banana", 2000, 1);
        Item savedItem = itemRepository.save(item);
        Long itemId = savedItem.getId();

        // when
        Item updateItem = new Item("apple", 1000, 2);
        itemRepository.update(itemId, updateItem);

        // then
        Item findById = itemRepository.findById(itemId);
        Assertions.assertThat(findById.getItemName()).isEqualTo(updateItem.getItemName());
        Assertions.assertThat(findById.getPrice()).isEqualTo(updateItem.getPrice());
        Assertions.assertThat(findById.getQuantity()).isEqualTo(updateItem.getQuantity());
    }
}