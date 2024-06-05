package com.abc.ecommerce.mapper;

import org.springframework.stereotype.Service;

import com.abc.ecommerce.dto.OrderLineRequest;
import com.abc.ecommerce.dto.OrderLineResponse;
import com.abc.ecommerce.entity.Order;
import com.abc.ecommerce.entity.OrderLine;

@Service
public class OrderLineMapper {
    public OrderLine toOrderLine(OrderLineRequest request) {
        return OrderLine.builder()
                .productId(request.productId())
                .order(
                        Order.builder()
                                .id(request.orderId())
                                .build()
                )
                .quantity(request.quantity())
                .build();
    }

    public OrderLineResponse toOrderLineResponse(OrderLine orderLine) {
        return new OrderLineResponse(
                orderLine.getId(),
                orderLine.getQuantity()
        );
    }
}