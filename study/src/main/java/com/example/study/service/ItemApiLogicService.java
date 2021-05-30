package com.example.study.service;

import com.example.study.model.entity.Item;
import com.example.study.model.enumclass.ItemStatus;
import com.example.study.model.network.Header;
import com.example.study.model.network.Pagination;
import com.example.study.model.network.request.ItemApiRequest;
import com.example.study.model.network.response.ItemApiResponse;
import com.example.study.repository.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemApiLogicService extends BaseService<ItemApiRequest, ItemApiResponse, Item> {
    @Autowired
    private PartnerRepository partnerRepository;

    //fetch req data
    @Override
    public Header<ItemApiResponse> create(Header<ItemApiRequest> request) {

        ItemApiRequest requestBody = request.getData();

        Item item = Item.builder()
                .status(ItemStatus.REGISTERED)
                .name(requestBody.getName())
                .title(requestBody.getTitle())
                .content(requestBody.getContent())
                .price(requestBody.getPrice())
                .brandName(requestBody.getBrandName())
                .registeredAt(LocalDateTime.now())
                .partner(partnerRepository.getOne(requestBody.getPartnerId()))
                .build();

        Item newItem = baseRepository.save(item);

        return Header.OK(response(newItem));
    }

    @Override
    public Header<ItemApiResponse> read(Long id) {
        return baseRepository.findById(id)
                .map(item -> Header.OK(response(item)))
                .orElseGet(() -> Header.ERROR("NO DATA"));
    }

    @Override
    public Header<ItemApiResponse> update(Header<ItemApiRequest> request) {
        ItemApiRequest itemApiRequest = request.getData();

        Optional<Item> foundItem = baseRepository.findById(itemApiRequest.getId());
        return foundItem.map(item -> {
            item.setStatus(itemApiRequest.getStatus()).
                    setName(itemApiRequest.getName()).
                    setTitle(itemApiRequest.getTitle()).
                    setContent(itemApiRequest.getContent()).
                    setPrice(itemApiRequest.getPrice()).
                    setBrandName(itemApiRequest.getBrandName()).
                    setRegisteredAt(itemApiRequest.getRegisteredAt()).
                    setUnregisteredAt(itemApiRequest.getUnregisteredAt());
            return item; //updated item returned
        })
                .map(modifiedItem -> baseRepository.save(modifiedItem))
                .map(updatedItem -> Header.OK(response(updatedItem)))
                .orElseGet(() -> Header.ERROR("NO DATA"));
    }

    @Override
    public Header delete(Long id) {
        return baseRepository.findById(id)
                .map(item -> {
                    baseRepository.delete(item);
                    return Header.OK();
                })
                .orElseGet(() -> Header.ERROR("NO DATA"));
    }

    @Override
    public Header<List<ItemApiResponse>> search(Pageable pageable) {
        Page<Item> items = baseRepository.findAll(pageable);
        List<ItemApiResponse> itemApiResponseList = items.stream()
                .map(orderGroup -> response(orderGroup))
                .collect(Collectors.toList());
        Pagination pagination = Pagination.builder()
                .totalPages(items.getTotalPages())
                .totalElements(items.getTotalElements())
                .currentPage(items.getNumber())
                .currentElements(items.getNumberOfElements())
                .build();

        return Header.OK(itemApiResponseList, pagination);
    }

    public ItemApiResponse response(Item item) {
        ItemApiResponse responseBody = ItemApiResponse.builder()
                .id(item.getId())
                .status(item.getStatus())
                .name(item.getName())
                .title(item.getTitle())
                .content(item.getContent())
                .price(item.getPrice())
                .brandName(item.getBrandName())
                .registeredAt(item.getRegisteredAt())
                .unregisteredAt(item.getUnregisteredAt())
                .partnerId(item.getPartner().getId())
                .build();

        return responseBody;
    }
}
