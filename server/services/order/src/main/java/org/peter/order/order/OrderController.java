package org.peter.order.order;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.peter.order.dto.OrderRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Integer> createOrder(@RequestBody @Valid OrderRequest orderRequest,
                                               @RequestHeader("loggedInUserId") String loggedInUserId) {
        return new ResponseEntity<>(orderService.createOrder(orderRequest, loggedInUserId), HttpStatus.OK);
    }
}