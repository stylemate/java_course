package com.fastcampus.javaallinone.project2.mycontact.repository;

import com.fastcampus.javaallinone.project2.mycontact.domain.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockRepository extends JpaRepository<Block, Long> {
}
