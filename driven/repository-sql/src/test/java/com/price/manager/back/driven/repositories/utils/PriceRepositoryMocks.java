package com.price.manager.back.driven.repositories.utils;

import com.price.manager.back.application.utils.DateUtil;
import com.price.manager.back.domain.Price;
import com.price.manager.back.driven.repositories.models.PriceEntity;

import java.util.List;

public class PriceRepositoryMocks {


    public PriceEntity createPriceEntity() {
        return PriceEntity.builder()
                .brandId(1L)
                .startDate(DateUtil.getDateFromString("2020-06-14 00:00:00"))
                .endDate(DateUtil.getDateFromString("2020-06-14 23:59:59"))
                .priceList(1L)
                .productId(35455L)
                .priority(0)
                .price(35.5)
                .curr("EUR")
                .build();
    }

    public Price createPrice() {
        return Price.builder()
                .brandId(1L)
                .startDate(DateUtil.getDateFromString("2020-06-14 00:00:00"))
                .endDate(DateUtil.getDateFromString("2020-06-14 23:59:59"))
                .priceList(1L)
                .productId(35455L)
                .priority(0)
                .price(35.5)
                .curr("EUR")
                .build();
    }

    public List<PriceEntity> mockEntityListTest() {
        return List.of(createPriceEntity());
    }
}
