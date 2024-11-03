package com.price.manager.back.driving.controllers.models;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class PriceResponse {

    private Long id;

    private Long brandId;

    private Double price;

    private String startDate;

    private String endDate;
}
