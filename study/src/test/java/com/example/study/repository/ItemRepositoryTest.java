package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Optional;

public class ItemRepositoryTest extends StudyApplicationTests {
    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void create() {
        Item item = new Item();
        item.setName("Laptop");
        item.setTitle("Title");
        item.setPrice(new BigDecimal(1000000));
        item.setContent("Samsung Laptop");
        //item.setPartnerId(1L);

        Item newItem = itemRepository.save(item);
        Assertions.assertNotNull(newItem);
    }

    @Test
    public void read() {
        Optional<Item> item = itemRepository.findById(1L);

        Assertions.assertTrue(item.isPresent());

        item.ifPresent(foundItem -> {
            System.out.println(foundItem);
        });
    }
}
