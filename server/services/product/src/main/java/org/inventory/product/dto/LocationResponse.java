package org.inventory.product.dto;

public record LocationResponse(
        String address,
        String city,
        String postalCode

) {
}
