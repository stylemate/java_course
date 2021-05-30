package com.example.study.service;

import com.example.study.model.entity.Settlement;
import com.example.study.model.network.Header;
import com.example.study.model.network.Pagination;
import com.example.study.model.network.request.SettlementApiRequest;
import com.example.study.model.network.response.SettlementApiResponse;
import com.example.study.model.network.response.UserOrderInfoApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SettlementApiLogicService extends BaseService<SettlementApiRequest, SettlementApiResponse, Settlement> {
    @Autowired
    UserApiLogicService userApiLogicService;

    @Override
    public Header<SettlementApiResponse> create(Header<SettlementApiRequest> req) {
        SettlementApiRequest requestBody = req.getData();

        UserOrderInfoApiResponse userOrderInfoApiResponse = userApiLogicService.orderInfo(requestBody.getId()).getData();
        BigDecimal totalPrice = userOrderInfoApiResponse.getUserApiResponse().getOrderGroupApiResponseList().stream()
                .map(orderGroup -> orderGroup.getTotalPrice())
                .reduce(BigDecimal.valueOf(0), (a, b)->a.add(b));

        Settlement settlement = Settlement.builder()
                .userId(requestBody.getId())
                .price(totalPrice)
                .build();

        Settlement newSettlement = baseRepository.save(settlement);
        return Header.OK(response(newSettlement));
    }

    @Override
    public Header<SettlementApiResponse> read(Long id) {
        return baseRepository.findById(id)
                .map(settlement -> Header.OK(response(settlement)))
                .orElseGet(() -> Header.ERROR("NO DATA"));
    }

    @Override
    public Header<SettlementApiResponse> update(Header<SettlementApiRequest> req) {
        //업데이트 보다 그냥 create 메소드를 계속 쓰는게 나을것 같다.
        //JPA가 중복은 자동으로 update 처리해주지 않나?
        SettlementApiRequest requestBody = req.getData();

        Optional<Settlement> foundSettlement = baseRepository.findById(requestBody.getId());
        return foundSettlement.map(settlement -> {
            settlement.setPrice(requestBody.getPrice());
            return settlement; //updated user returned
        })
                .map(modifiedSettlement -> baseRepository.save(modifiedSettlement))
                .map(updatedSettlement -> Header.OK(response(updatedSettlement)))
                .orElseGet(() -> Header.ERROR("NO DATA"));
    }

    @Override
    public Header delete(Long id) {
        return baseRepository.findById(id)
                .map(settlement -> {
                    baseRepository.delete(settlement);
                    return Header.OK();
                })
                .orElseGet(() -> Header.ERROR("NO DATA"));
    }

    @Override
    public Header<List<SettlementApiResponse>> search(Pageable pageable) {
        Page<Settlement> settlements = baseRepository.findAll(pageable);
        List<SettlementApiResponse> settlementApiResponseList = settlements.stream()
                .map(settlement -> response(settlement))
                .collect(Collectors.toList());
        Pagination pagination = Pagination.builder()
                .totalPages(settlements.getTotalPages())
                .totalElements(settlements.getTotalElements())
                .currentPage(settlements.getNumber())
                .currentElements(settlements.getNumberOfElements())
                .build();

        return Header.OK(settlementApiResponseList, pagination);
    }

    private SettlementApiResponse response(Settlement settlement) {
        SettlementApiResponse settlementApiResponse = SettlementApiResponse.builder()
                .id(settlement.getUserId())
                .price(settlement.getPrice())
                .build();
        //Header + Data 안함
        return settlementApiResponse;
    }
}
