package com.price.manager.back.driving.controllers.utils;

import com.price.manager.back.domain.Price;
import com.price.manager.back.driving.controllers.models.PriceResponse;

import java.time.LocalDateTime;

public class PriceMocks {


    public Price createPrice() {
        return Price.builder()
                .priceList(2L)
                .brandId(1L)
                .productId(35455L)
                .price(25.45)
                .startDate(LocalDateTime.of(2020, 6, 14, 15, 0, 0))
                .endDate(LocalDateTime.of(2020,6,14,18,30,0))
                .build();
    }


    public PriceResponse createPriceResponse1() {
        return PriceResponse.builder()
                .id(1L)
                .brandId(1L)
                .price(35.50)
                .startDate("2020-06-14 00:00:00")
                .endDate("2020-12-31 23:59:59")
                .build();
    }

    public PriceResponse createPriceResponse2() {
        return PriceResponse.builder()
                .id(2L)
                .brandId(1L)
                .price(25.45)
                .startDate("2020-06-14 15:00:00")
                .endDate("2020-06-14 18:30:00")
                .build();
    }

    public PriceResponse createPriceResponse3() {
        return PriceResponse.builder()
                .id(3L)
                .brandId(1L)
                .price(30.50)
                .startDate("2020-06-15 00:00:00")
                .endDate("2020-06-15 11:00:00")
                .build();
    }

    public PriceResponse createPriceResponse4() {
        return PriceResponse.builder()
                .id(4L)
                .brandId(1L)
                .price(38.95)
                .startDate("2020-06-15 16:00:00")
                .endDate("2020-12-31 23:59:59")
                .build();
    }
}
