package org.peter.order.helpers;

import org.peter.order.dto.OrderRequest;
import org.peter.order.order.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderMapper {

    public Order toOrder(OrderRequest orderRequest, String loggedInUserId) {
        return Order.builder()
                .customerId(orderRequest.customerId())
                .reference(orderRequest.reference())
                .totalPrice(orderRequest.totalAmount())
                .paymentMethod(orderRequest.paymentMethod())
                .registeredCustomerId(Integer.parseInt(loggedInUserId))
                .build();
    }
}
