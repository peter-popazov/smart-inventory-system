package org.peter.order.helpers;

import lombok.RequiredArgsConstructor;
import org.peter.order.dto.OrderLineRequest;
import org.peter.order.line.OrderLine;
import org.peter.order.order.OrderRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderLineMapper {

    private final OrderRepository orderRepository;

    public OrderLine toOrderLine(OrderLineRequest orderLineRequest) {
        return OrderLine.builder()
                .productId(orderLineRequest.productId())
                .quantity(orderLineRequest.quantity())
                .unitPrice(orderLineRequest.unitPrice())
                .order(orderRepository.findById(orderLineRequest.orderId())
                        .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderLineRequest.orderId())))
                .build();
    }
}
