package com.example.study.service;

import com.example.study.model.entity.OrderGroup;
import com.example.study.model.entity.User;
import com.example.study.model.enumclass.UserStatus;
import com.example.study.model.network.Header;
import com.example.study.model.network.Pagination;
import com.example.study.model.network.request.UserApiRequest;
import com.example.study.model.network.response.ItemApiResponse;
import com.example.study.model.network.response.OrderGroupApiResponse;
import com.example.study.model.network.response.UserApiResponse;
import com.example.study.model.network.response.UserOrderInfoApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserApiLogicService extends BaseService<UserApiRequest, UserApiResponse, User> {

    @Autowired
    private OrderGroupApiLogicService orderGroupApiLogicService;

    @Autowired
    private ItemApiLogicService itemApiLogicService;

    //fetch req data
    @Override
    public Header<UserApiResponse> create(Header<UserApiRequest> request) {
        UserApiRequest userApiRequest = request.getData();

        User user = User.builder()
                .account(userApiRequest.getAccount())
                .password(userApiRequest.getPassword())
                .status(UserStatus.REGISTERED)
                .phoneNumber(userApiRequest.getPhoneNumber())
                .email(userApiRequest.getEmail())
                .registeredAt(LocalDateTime.now())
                .build();
        User newUser = baseRepository.save(user);


        return Header.OK(response(newUser));
    }

    @Override
    public Header<UserApiResponse> read(Long id) {
//        Optional<User> foundUser = baseRepository.findById(id);
//        이게 안되네
//        foundUser.ifPresent(user -> {
//            return response(user);
//        });
//        return Header.ERROR("NO DATA");
        return baseRepository.findById(id)
                .map(user -> Header.OK(response(user)))
                .orElseGet(() -> Header.ERROR("NO DATA"));
    }

    @Override
    public Header<UserApiResponse> update(Header<UserApiRequest> request) {
        UserApiRequest userApiRequest = request.getData();

        Optional<User> foundUser = baseRepository.findById(userApiRequest.getId());
        return foundUser.map(user -> {
            user.setAccount(userApiRequest.getAccount()).
                    setPassword(userApiRequest.getPassword()).
                    setStatus(userApiRequest.getStatus()).
                    setPhoneNumber(userApiRequest.getPhoneNumber()).
                    setEmail(userApiRequest.getEmail()).
                    setRegisteredAt(userApiRequest.getRegisteredAt()).
                    setUnregisteredAt(userApiRequest.getUnregisteredAt());
            return user; //updated user returned
        })
                .map(modifiedUser -> baseRepository.save(modifiedUser))
                .map(updatedUser -> Header.OK(response(updatedUser)))
                .orElseGet(() -> Header.ERROR("NO DATA"));
    }

    @Override
    public Header delete(Long id) {
        return baseRepository.findById(id)
                .map(user -> {
                    baseRepository.delete(user);
                    return Header.OK();
                })
                .orElseGet(() -> Header.ERROR("NO DATA"));
    }

    @Override
    public Header<List<UserApiResponse>> search(Pageable pageable) {
        Page<User> users = baseRepository.findAll(pageable);
        List<UserApiResponse> userApiResponseList = users.stream()
                .map(user -> response(user))
                .collect(Collectors.toList());
        Pagination pagination = Pagination.builder()
                .totalPages(users.getTotalPages())
                .totalElements(users.getTotalElements())
                .currentPage(users.getNumber())
                .currentElements(users.getNumberOfElements())
                .build();

        return Header.OK(userApiResponseList, pagination);
    }

    //user만 있는거니까 baseService에서 구현안해도 될듯?
    public Header<UserOrderInfoApiResponse> orderInfo(Long id) {
        User user = baseRepository.getOne(id);
        UserApiResponse userApiResponse = response(user);


        List<OrderGroup> orderGroupList = user.getOrderGroupList();
        List<OrderGroupApiResponse> orderGroupApiResponseList = orderGroupList.stream()
                .map(orderGroup -> {
                    OrderGroupApiResponse orderGroupApiResponse = orderGroupApiLogicService.response(orderGroup);
                    List<ItemApiResponse> itemApiResponseList = orderGroup.getOrderDetailList().stream()
                            .map(detail -> detail.getItem())
                            .map(item -> itemApiLogicService.response(item))
                            .collect(Collectors.toList());
                    orderGroupApiResponse.setItemApiResponseList(itemApiResponseList);
                    return orderGroupApiResponse;
                })
                .collect(Collectors.toList());
        userApiResponse.setOrderGroupApiResponseList(orderGroupApiResponseList);

        UserOrderInfoApiResponse userOrderInfoApiResponse = UserOrderInfoApiResponse.builder()
                .userApiResponse(userApiResponse)
                .build();

        return Header.OK(userOrderInfoApiResponse);
    }

    private UserApiResponse response(User user) {
        UserApiResponse userApiResponse = UserApiResponse.builder()
                .id(user.getId())
                .account(user.getAccount())
                .password(user.getPassword())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .status(user.getStatus())
                .registeredAt(user.getRegisteredAt())
                .unregisteredAt(user.getUnregisteredAt())
                .build();
        //Header + Data
        return userApiResponse;
    }
}
