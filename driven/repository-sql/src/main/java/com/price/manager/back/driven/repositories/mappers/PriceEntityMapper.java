package com.price.manager.back.driven.repositories.mappers;


import com.price.manager.back.domain.Price;
import com.price.manager.back.driven.repositories.models.PriceEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceEntityMapper {

  Price toDomain(PriceEntity entity);

}
