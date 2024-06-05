package com.abc.ecommerce.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.abc.ecommerce.dto.OrderLineRequest;
import com.abc.ecommerce.dto.OrderLineResponse;
import com.abc.ecommerce.entity.OrderLine;
import com.abc.ecommerce.mapper.OrderLineMapper;
import com.abc.ecommerce.repo.OrderLineRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderLineService {

    private final OrderLineRepository repository;
    private final OrderLineMapper mapper;

    public void saveOrderLine(List<OrderLineRequest> request) {
    	List<OrderLine> collect = request.stream().map(mapper::toOrderLine).collect(Collectors.toList());
         repository.saveAll(collect);
    }

    public List<OrderLineResponse> findAllByOrderId(Integer orderId) {
        return repository.findAllByOrderId(orderId)
                .stream()
                .map(mapper::toOrderLineResponse)
                .collect(Collectors.toList());
    }


}