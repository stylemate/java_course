package com.fastcampus.javaallinone.project2.mycontact.repository;

import com.fastcampus.javaallinone.project2.mycontact.domain.Block;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BlockRepositoryTest {
    @Autowired
    private BlockRepository blockRepository;

    @Test
    void crud() {
        Block block = new Block();
        block.setName("Justin");
        block.setReason("Stalker");
        block.setStartDate(LocalDate.now());
        block.setEndDate(LocalDate.now());

        blockRepository.save(block);

        Optional<Block> foundBlock = blockRepository.findById(1L);

        Assertions.assertEquals(foundBlock.get().getName(), "Justin");
    }

}