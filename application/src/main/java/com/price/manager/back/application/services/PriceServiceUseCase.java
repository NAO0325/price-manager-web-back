package com.price.manager.back.application.services;


import com.price.manager.back.application.ports.driven.PriceRepositoryPort;
import com.price.manager.back.application.ports.driving.PriceServicePort;
import com.price.manager.back.application.utils.DateUtil;
import com.price.manager.back.domain.Price;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PriceServiceUseCase implements PriceServicePort {

    private final PriceRepositoryPort priceRepositoryPort;

    @Override
    public Price findByBrandProductBetweenDate(String brandId, String productId, String dateBetween) {
        List<Price> prices = listAllByBrandProductBetweenDate(Long.parseLong(brandId), Long.parseLong(productId), dateBetween);
        if (prices.isEmpty()) {
            return null;
        }
        if (prices.size() > 1) {
            //Se dejan los elementos de mayor prioridad
            prices = filterSimilarElementsByPriority(prices);
            //Si existe mas de uno aún, se dejara al elemento con mayor priceList
            prices = filterSimilarElementsByPriceList(prices);
            //Se devuelve un unico objeto
            return prices.get(0);
        }
        return prices.get(0);
    }

    private List<Price> listAllByBrandProductBetweenDate(Long brandId, Long productId, String dateBetween) {
        LocalDateTime dateToDateObj = DateUtil.getDateFromString(dateBetween);
        if (dateToDateObj == null) {
            return new ArrayList<>();
        }
        return priceRepositoryPort.findAllByBrandIdAndProductIdBetweenDates(brandId, productId, dateToDateObj);
    }

    private List<Price> filterSimilarElementsByPriority(List<Price> prices) {
        //Se recupera valor mas alto del campo
        Integer maxPriority = getMaxPriority(prices);
        //Se devuelve lista con filtro aplicado
        return prices.stream()
                .filter(price -> price.getPriority() >= maxPriority)
                .collect(Collectors.toList());
    }

    private List<Price> filterSimilarElementsByPriceList(List<Price> prices) {
        //Se recupera valor mas alto del campo
        Long maxPriceList = getMaxPriceList(prices);
        //Se devuelve lista con filtro aplicado
        return prices.stream()
                .filter(price -> price.getPriceList() >= maxPriceList)
                .collect(Collectors.toList());
    }

    private Long getMaxPriceList(List<Price> prices) {
        return prices.stream()
                .max(Comparator.comparing(Price::getPriceList))
                .orElseGet(Price::new)
                .getPriceList();
    }

    private Integer getMaxPriority(List<Price> prices) {
        return prices.stream()
                .max(Comparator.comparing(Price::getPriority))
                .orElseGet(Price::new)
                .getPriority();
    }

}
