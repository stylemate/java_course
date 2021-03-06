package com.example.study.service;

import com.example.study.model.entity.OrderGroup;
import com.example.study.model.enumclass.OrderType;
import com.example.study.model.network.Header;
import com.example.study.model.network.Pagination;
import com.example.study.model.network.request.OrderGroupApiRequest;
import com.example.study.model.network.response.OrderGroupApiResponse;
import com.example.study.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderGroupApiLogicService extends BaseService<OrderGroupApiRequest, OrderGroupApiResponse, OrderGroup> {

    @Autowired
    private UserRepository userRepository;

    //fetch req data
    @Override
    public Header<OrderGroupApiResponse> create(Header<OrderGroupApiRequest> request) {

        OrderGroupApiRequest requestBody = request.getData();

        OrderGroup orderGroup = OrderGroup.builder()
                .status(requestBody.getStatus())
                .orderType(OrderType.ALL)
                .revAddress(requestBody.getRevAddress())
                .revName(requestBody.getRevName())
                .paymentType(requestBody.getPaymentType())
                .totalPrice(requestBody.getTotalPrice())
                .totalQuantity(requestBody.getTotalQuantity())
                .orderAt(LocalDateTime.now())
//                .arrivalDate(requestBody.getArrivalDate())
                .user(userRepository.getOne(requestBody.getUserId()))
                .build();

        OrderGroup newOrderGroup = baseRepository.save(orderGroup);

        return Header.OK(response(newOrderGroup));
    }

    @Override
    public Header<OrderGroupApiResponse> read(Long id) {
        return baseRepository.findById(id)
                .map(orderGroup -> Header.OK(response(orderGroup)))
                .orElseGet(() -> Header.ERROR("NO DATA"));
    }

    @Override
    public Header<OrderGroupApiResponse> update(Header<OrderGroupApiRequest> request) {
        OrderGroupApiRequest requestBody = request.getData();

        Optional<OrderGroup> foundOrderGroup = baseRepository.findById(requestBody.getId());

        return foundOrderGroup.map(orderGroup -> {
            orderGroup.setStatus(requestBody.getStatus()).
                    setOrderType(requestBody.getOrderType()).
                    setRevAddress(requestBody.getRevAddress()).
                    setRevName(requestBody.getRevName()).
                    setPaymentType(requestBody.getPaymentType()).
                    setTotalPrice(requestBody.getTotalPrice()).
                    setTotalQuantity(requestBody.getTotalQuantity()).
                    setOrderAt(requestBody.getOrderAt()).
                    setArrivalDate(requestBody.getArrivalDate()).
                    setUser(userRepository.getOne(requestBody.getUserId()));
            return orderGroup; //updated orderGroup returned
        })
                .map(modifiedOrderGroup -> baseRepository.save(modifiedOrderGroup))
                .map(updatedOrderGroup -> Header.OK(response(updatedOrderGroup)))
                .orElseGet(() -> Header.ERROR("NO DATA"));
    }

    @Override
    public Header delete(Long id) {
        return baseRepository.findById(id)
                .map(orderGroup -> {
                    baseRepository.delete(orderGroup);
                    return Header.OK();
                })
                .orElseGet(() -> Header.ERROR("NO DATA"));
    }

    @Override
    public Header<List<OrderGroupApiResponse>> search(Pageable pageable) {
        Page<OrderGroup> orderGroups = baseRepository.findAll(pageable);
        List<OrderGroupApiResponse> orderApiResponseList = orderGroups.stream()
                .map(orderGroup -> response(orderGroup))
                .collect(Collectors.toList());
        Pagination pagination = Pagination.builder()
                .totalPages(orderGroups.getTotalPages())
                .totalElements(orderGroups.getTotalElements())
                .currentPage(orderGroups.getNumber())
                .currentElements(orderGroups.getNumberOfElements())
                .build();

        return Header.OK(orderApiResponseList, pagination);
    }

    public OrderGroupApiResponse response(OrderGroup orderGroup) {
        OrderGroupApiResponse responseBody = OrderGroupApiResponse.builder()
                .id(orderGroup.getId())
                .status(orderGroup.getStatus())
                .orderType(orderGroup.getOrderType())
                .revAddress(orderGroup.getRevAddress())
                .revName(orderGroup.getRevName())
                .paymentType(orderGroup.getRevName())
                .totalPrice(orderGroup.getTotalPrice())
                .totalQuantity(orderGroup.getTotalQuantity())
                .orderAt(orderGroup.getOrderAt())
//                .arrivalDate(orderGroup.getArrivalDate())
                .userId(orderGroup.getUser().getId())
                .build();

        return responseBody;
    }
}
