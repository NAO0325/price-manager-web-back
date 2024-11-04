package com.price.manager.back.driving.controllers.utils;

import com.price.manager.back.domain.Price;

import java.time.LocalDateTime;

public class PriceMocks {

    public Price createPrice() {
        return Price.builder()
                .priceList(2L)
                .brandId(1L)
                .productId(35455L)
                .price(25.45)
                .startDate(LocalDateTime.of(2022, 1, 1, 1, 30, 59))
                .endDate(LocalDateTime.of(2022,1,31,1,30,59))
                .build();
    }
}
