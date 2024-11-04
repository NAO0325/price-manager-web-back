package com.price.manager.back.application.utils;

import com.price.manager.back.domain.Price;

import java.util.List;

public class PriceDomainMocks {

    public List<Price> mockListTest1() {
        return List.of(Price.builder()
                .brandId(1L)
                .startDate(DateUtil.getDateFromString("2020-06-14 00:00:00"))
                .endDate(DateUtil.getDateFromString("2020-06-14 23:59:59"))
                .priceList(1L)
                .productId(35455L)
                .priority(0)
                .price(35.5)
                .curr("EUR")
                .build());
    }

    public List<Price> mockListTest2() {
        return List.of(
                Price.builder()
                        .brandId(1L)
                        .startDate(DateUtil.getDateFromString("2020-06-14 15:00:00"))
                        .endDate(DateUtil.getDateFromString("2020-06-14 18:30:00"))
                        .priceList(2L)
                        .productId(35455L)
                        .priority(1)
                        .price(25.45)
                        .curr("EUR")
                        .build(),
                Price.builder()
                        .brandId(1L)
                        .startDate(DateUtil.getDateFromString("2020-06-14 00:00:00"))
                        .endDate(DateUtil.getDateFromString("2020-12-31 23:59:59"))
                        .priceList(1L)
                        .productId(35455L)
                        .priority(0)
                        .price(35.5)
                        .curr("EUR")
                        .build()
        );
    }

    public List<Price> mockListTest3() {
        return List.of(
                Price.builder()
                        .brandId(1L)
                        .startDate(DateUtil.getDateFromString("2020-06-14 00:00:00"))
                        .endDate(DateUtil.getDateFromString("2020-12-31 23:59:59"))
                        .priceList(1L)
                        .productId(35455L)
                        .priority(0)
                        .price(35.5)
                        .curr("EUR")
                        .build()
        );
    }

    public List<Price> mockListTest4() {
        return List.of(
                Price.builder()
                        .brandId(1L)
                        .startDate(DateUtil.getDateFromString("2020-06-15 00:00:00"))
                        .endDate(DateUtil.getDateFromString("2020-06-15 11:00:00"))
                        .priceList(3L)
                        .productId(35455L)
                        .priority(1)
                        .price(30.5)
                        .curr("EUR")
                        .build(),
                Price.builder()
                        .brandId(1L)
                        .startDate(DateUtil.getDateFromString("2020-06-14 00:00:00"))
                        .endDate(DateUtil.getDateFromString("2020-12-31 23:59:59"))
                        .priceList(1L)
                        .productId(35455L)
                        .priority(0)
                        .price(35.5)
                        .curr("EUR")
                        .build()
        );
    }

    public List<Price> mockListTest5() {
        return List.of(
                Price.builder()
                        .brandId(1L)
                        .startDate(DateUtil.getDateFromString("2020-06-15 16:00:00"))
                        .endDate(DateUtil.getDateFromString("2020-12-31 23:59:59"))
                        .priceList(1L)
                        .productId(35455L)
                        .priority(1)
                        .price(38.95)
                        .curr("EUR")
                        .build(),
                Price.builder()
                        .brandId(1L)
                        .startDate(DateUtil.getDateFromString("2020-06-14 00:00:00"))
                        .endDate(DateUtil.getDateFromString("2020-12-31 23:59:59"))
                        .priceList(1L)
                        .productId(35455L)
                        .priority(0)
                        .price(35.5)
                        .curr("EUR")
                        .build()
        );
    }
}
