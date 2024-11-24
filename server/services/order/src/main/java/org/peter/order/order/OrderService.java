package org.peter.order.order;

import lombok.RequiredArgsConstructor;
import org.peter.order.client.CustomerClient;
import org.peter.order.client.ProductClient;
import org.peter.order.dto.*;
import org.peter.order.exception.CustomerNotFoundException;
import org.peter.order.helpers.OrderMapper;
import org.peter.order.line.OrderLineRepository;
import org.peter.order.line.OrderLineService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderLineService orderLineService;
    private final OrderLineRepository orderLineRepository;

    public Integer createOrder(OrderRequest orderRequest, String loggedInUserId) {
        CustomerResponse customer = customerClient.findCustomerById(orderRequest.customerId(), loggedInUserId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id " + orderRequest.customerId()));

        productClient.purchaseProducts(orderRequest.products(), loggedInUserId);

        Order order = orderRepository.save(orderMapper.toOrder(orderRequest, loggedInUserId));

        for (PurchaseProductsRequest request : orderRequest.products()) {
            orderLineService.saveOrderLine(
                    OrderLineRequest.builder()
                            .orderId(order.getOrderId())
                            .productId(request.productId())
                            .warehouseId(request.warehouseId())
                            .quantity(request.quantity())
                            .unitPrice(request.unitPrice())
                            .build()
            );

            productClient.addMovementForProduct(StockMovementsRequest.builder()
                    .movementType(StockMovementType.SALE)
                    .productId(request.productId())
                    .warehouseId(request.warehouseId())
                    .quantity(-request.quantity())
                    .build());
        }

        return order.getOrderId();
    }

    public BigDecimal getTotalEarnings(String userId) {
        BigDecimal totalEarnings = orderRepository.calculateTotalEarningsByOwner(Integer.parseInt(userId));
        return totalEarnings != null ? totalEarnings : BigDecimal.ZERO;
    }
}
