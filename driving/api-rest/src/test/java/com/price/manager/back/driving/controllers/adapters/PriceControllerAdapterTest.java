package com.price.manager.back.driving.controllers.adapters;

import com.price.manager.back.application.exceptions.PriceNotFoundException;
import com.price.manager.back.application.ports.driving.PriceServicePort;
import com.price.manager.back.domain.Price;
import com.price.manager.back.driving.controllers.mappers.PriceMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PriceControllerAdapterTest {

    @Mock
    private PriceServicePort priceServicePort;

    @Mock
    private PriceMapper priceMapper;

    @InjectMocks
    private PriceControllerAdapter priceRestController;

    @Test
    void returnPriceDataOK() {
        var dateTest = LocalDateTime.of(2020,6,14, 10, 0, 0); //"2020-06-14 10:00:00";
        when(priceServicePort.findByBrandProductBetweenDate(anyLong(), anyLong(), any()))
                .thenReturn(Price.builder().startDate(LocalDateTime.now()).build());
        var result = priceRestController.findByBrandProductBetweenDate(2L, 2L, dateTest);
        assertNotNull(result);
    }

    @Test
    void returnPriceNotFoundException() {
        var dateTest = LocalDateTime.of(2020,6,14, 10, 0, 0); //"2020-06-14 10:00:00";
        willThrow(new PriceNotFoundException(3L, 1L, dateTest))
                .given(priceServicePort).findByBrandProductBetweenDate(anyLong(), anyLong(), any());
        assertThrows(PriceNotFoundException.class, () -> {
            priceRestController.findByBrandProductBetweenDate(3L, 1L, dateTest);
        });
    }
}
