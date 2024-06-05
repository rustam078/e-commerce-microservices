package com.abc.ecommerce.kafkaconsumer;

import static java.lang.String.format;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.abc.ecommerce.email.EmailService;
import com.abc.ecommerce.entity.Customer;
import com.abc.ecommerce.entity.Notification;
import com.abc.ecommerce.entity.NotificationType;
import com.abc.ecommerce.entity.OrderConfirmation;
import com.abc.ecommerce.entity.OrderConfirmationRequest;
import com.abc.ecommerce.entity.PaymentConfirmation;
import com.abc.ecommerce.entity.Product;
import com.abc.ecommerce.repository.NotificationRepository;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationsConsumer {

    private final NotificationRepository repository;
    private final EmailService emailService;
    @KafkaListener(topics = "payment-topic")
    public void consumePaymentSuccessNotifications(PaymentConfirmation paymentConfirmation) throws MessagingException {
        log.info(format("Consuming the message from payment-topic Topic:: %s", paymentConfirmation));
        log.info(format("Consuming the message from payment-topic Topic:: %s", paymentConfirmation.getTotalAmount()));
        repository.save(
                Notification.builder()
                        .type(NotificationType.PAYMENT_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .paymentConfirmation(paymentConfirmation)
                        .build()
        );
        var customerName = paymentConfirmation.getCustomerFirstname() + " " + paymentConfirmation.getCustomerLastname();
        emailService.sendPaymentSuccessEmail(
                paymentConfirmation.getCustomerEmail(),
                customerName,
                paymentConfirmation.getTotalAmount(),
                paymentConfirmation.getOrderReference()
        );
    }

    @KafkaListener(topics = "order-topic")
    public void consumeOrderConfirmationNotifications(OrderConfirmationRequest orderConfirmation) throws MessagingException {
        log.info(format("Consuming the message from order-topic Topic:: %s", orderConfirmation));
        OrderConfirmation orderConfirmation2 = new OrderConfirmation();
        List<Product> productlist= new ArrayList<>();
        if(orderConfirmation!=null) {
        	orderConfirmation2.setCustomerId(orderConfirmation.customer().getId());
        	orderConfirmation2.setOrderReference(orderConfirmation.orderReference());
        	orderConfirmation2.setPaymentMethod(orderConfirmation.paymentMethod());
        	orderConfirmation2.setTotalAmount(orderConfirmation.totalAmount());
        	for(Product prod:orderConfirmation.products()) {
        		Product product = new Product();
        		product.setName(prod.getName());
        		product.setDescription(prod.getDescription());
        		product.setPrice(prod.getPrice());
        		product.setQuantity(prod.getQuantity());
        		product.setId(prod.getId());
        		log.info("Product id {}",prod.getProductId());
        		product.setProductId(prod.getProductId());
        		productlist.add(product);
        	}
        	orderConfirmation2.setProducts(productlist);
        }
        repository.save(
                Notification.builder()
                        .type(NotificationType.ORDER_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .orderConfirmation(orderConfirmation2)
                        .build()
        );
        var customerName = orderConfirmation.customer().getFirstname() + " " + orderConfirmation.customer().getLastname();
        emailService.sendOrderConfirmationEmail(
                orderConfirmation.customer().getEmail(),
                customerName,
                orderConfirmation.totalAmount(),
                orderConfirmation.orderReference(),
                orderConfirmation.products()
        );
    }
}