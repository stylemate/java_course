package com.example.study.service;

import com.example.study.ifs.CrudInterface;
import com.example.study.model.entity.User;
import com.example.study.model.enumclass.UserStatus;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.UserApiRequest;
import com.example.study.model.network.response.UserApiResponse;
import com.example.study.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserApiLogicService implements CrudInterface<UserApiRequest, UserApiResponse> {

    @Autowired
    private UserRepository userRepository;

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
        User newUser = userRepository.save(user);


        return response(newUser);
    }

    @Override
    public Header<UserApiResponse> read(Long id) {
//        Optional<User> foundUser = userRepository.findById(id);
//        이게 안되네
//        foundUser.ifPresent(user -> {
//            return response(user);
//        });
//        return Header.ERROR("NO DATA");
        return userRepository.findById(id)
                .map(user -> response(user))
                .orElseGet(() -> Header.ERROR("NO DATA"));
    }

    @Override
    public Header<UserApiResponse> update(Header<UserApiRequest> request) {
        UserApiRequest userApiRequest = request.getData();

        Optional<User> foundUser = userRepository.findById(userApiRequest.getId());
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
                .map(modifiedUser -> userRepository.save(modifiedUser))
                .map(updatedUser -> response(updatedUser))
                .orElseGet(() -> Header.ERROR("NO DATA"));
    }

    @Override
    public Header delete(Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(user);
                    return Header.OK();
                })
                .orElseGet(() -> Header.ERROR("NO DATA"));
    }

    private Header<UserApiResponse> response(User user) {
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
        return Header.OK(userApiResponse);
    }
}
