package com.price.manager.back.driving.controllers.mappers;

import com.price.manager.back.domain.Price;
import com.price.manager.back.driving.controllers.models.PriceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Mapper(componentModel = "spring")
public interface PriceMapper {

    @Mapping(target = "id", source = "priceList")
    @Mapping(target = "startDate", expression = "java(mapDateToString(price.getStartDate()))")
    @Mapping(target = "endDate", expression = "java(mapDateToString(price.getEndDate()))")
    PriceResponse toResponseDto(Price price);

    default String mapDateToString(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(formatter);
    }

}
