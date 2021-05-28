package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.Item;
import com.example.study.model.entity.OrderDetail;
import com.example.study.model.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

public class OrderDetailRepositoryTest extends StudyApplicationTests {
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void create() {
        OrderDetail orderDetail = new OrderDetail();
        Optional<User> user = userRepository.findById(1L);
        Optional<Item> item = itemRepository.findById(1L);

        orderDetail.setStatus("Instantly Delivered!");
        orderDetail.setArrivalDate(LocalDateTime.now()); //instant delivery
        orderDetail.setCreatedAt(LocalDateTime.now());
        orderDetail.setCreatedBy("me");

        OrderDetail newOrderDetail = orderDetailRepository.save(orderDetail);
        Assertions.assertNotNull(newOrderDetail);
    }
}
