package com.price.manager.back.application.exceptions;

import java.time.LocalDateTime;

public class PriceNotFoundException extends RuntimeException {

    public PriceNotFoundException(Long brandId, Long productId, LocalDateTime date) {
        super(String.format("Price not found for brand ID: %d, product ID: %d, date: %s",
                brandId, productId, date));
    }
}
