package org.peter.order.order;

import lombok.RequiredArgsConstructor;
import org.peter.order.client.CustomerClient;
import org.peter.order.client.ProductClient;
import org.peter.order.dto.CustomerResponse;
import org.peter.order.dto.OrderLineRequest;
import org.peter.order.dto.OrderRequest;
import org.peter.order.dto.PurchaseProductsRequest;
import org.peter.order.exception.CustomerNotFoundException;
import org.peter.order.helpers.OrderMapper;
import org.peter.order.line.OrderLineService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderLineService orderLineService;

    public Integer createOrder(OrderRequest orderRequest, String loggedInUserId) {
        // check if the customer exists
        CustomerResponse customer = customerClient.findCustomerById(orderRequest.customerId(), loggedInUserId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id " + orderRequest.customerId()));

        // purchase the product --> product ms
        productClient.purchaseProducts(orderRequest.products(), loggedInUserId);

        // persist order
        Order order = orderRepository.save(orderMapper.toOrder(orderRequest, loggedInUserId));

        // persist orderLines
        for (PurchaseProductsRequest request : orderRequest.products()) {
            orderLineService.saveOrderLine(
                    OrderLineRequest.builder()
                            .orderId(order.getOrderId())
                            .productId(request.productId())
                            .quantity(request.quantity())
                            .unitPrice(request.unitPrice())
                            .build()
            );
        }

        // todo start payment process

        // todo send order confirmation email --> notification ms
        return order.getOrderId();
    }
}
