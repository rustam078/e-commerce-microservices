package com.abc.ecommerce.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.abc.ecommerce.customer.CustomerClient;
import com.abc.ecommerce.dto.OrderLineRequest;
import com.abc.ecommerce.dto.OrderRequest;
import com.abc.ecommerce.dto.OrderResponse;
import com.abc.ecommerce.entity.Order;
import com.abc.ecommerce.exception.BusinessException;
import com.abc.ecommerce.kafka.OrderConfirmation;
import com.abc.ecommerce.kafka.OrderProducer;
import com.abc.ecommerce.mapper.OrderMapper;
import com.abc.ecommerce.payment.PaymentClient;
import com.abc.ecommerce.payment.PaymentRequest;
import com.abc.ecommerce.product.ProductClient;
import com.abc.ecommerce.product.PurchaseRequest;
import com.abc.ecommerce.product.PurchaseResponse;
import com.abc.ecommerce.repo.OrderRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository repository;
	private final OrderMapper mapper;
	private final CustomerClient customerClient;
	private final PaymentClient paymentClient;
	private final ProductClient productClient;
	private final OrderLineService orderLineService;
	private final OrderProducer orderProducer;

	@Transactional
	public Integer createOrder(OrderRequest request) {
		var customer = this.customerClient.findCustomerById(request.customerId()).orElseThrow(
				() -> new BusinessException("Cannot create order:: No customer exists with the provided ID"));

		 List<PurchaseResponse> purchaseProducts = productClient.purchaseProducts(request.products());
		double sum = purchaseProducts.stream()
											.mapToDouble(item -> item.price().doubleValue() * item.quantity()).sum();
										
		BigDecimal averageBigDecimal = BigDecimal.valueOf(sum);
		Order order2 = mapper.toOrder(request);
		order2.setTotalAmount(averageBigDecimal);
		var order = this.repository.save(order2);
		List<OrderLineRequest> orderLineRequests = new ArrayList<>();
		for (PurchaseRequest purchaseRequest : request.products()) {
			OrderLineRequest orderLineRequest = new OrderLineRequest(null, order.getId(), purchaseRequest.productId(),
					purchaseRequest.quantity());
			orderLineRequests.add(orderLineRequest);
		}
		orderLineService.saveOrderLine(orderLineRequests);
		var paymentRequest = new PaymentRequest(order.getTotalAmount(), request.paymentMethod(), order.getId(),
				order.getReference(), customer);
		paymentClient.requestOrderPayment(paymentRequest);

	
		orderProducer.sendOrderConfirmation(new OrderConfirmation(request.reference(), order.getTotalAmount(),
				request.paymentMethod(), customer, purchaseProducts));

		return order.getId();
	}

	public List<OrderResponse> findAllOrders() {
		return this.repository.findAll().stream().map(this.mapper::fromOrder).collect(Collectors.toList());
	}

	public OrderResponse findById(Integer id) {
		return this.repository.findById(id).map(this.mapper::fromOrder).orElseThrow(
				() -> new EntityNotFoundException(String.format("No order found with the provided ID: %d", id)));
	}
}