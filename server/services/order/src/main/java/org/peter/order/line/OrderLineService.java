package org.peter.order.line;

import lombok.RequiredArgsConstructor;
import org.peter.order.dto.OrderLineRequest;
import org.peter.order.helpers.OrderLineMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderLineService {

    private final OrderLineRepository orderLineRepository;
    private final OrderLineMapper orderLineMapper;

    public void saveOrderLine(OrderLineRequest orderLineRequest) {
        orderLineRepository.save(orderLineMapper.toOrderLine(orderLineRequest));
    }
}
