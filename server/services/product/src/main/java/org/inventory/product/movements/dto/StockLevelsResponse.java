package org.inventory.product.movements.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record StockLevelsResponse(

        Integer level,
        LocalDate date
) {
}
