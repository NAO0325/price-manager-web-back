package com.price.manager.back.driving.controllers.adapters;

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
import static org.mockito.ArgumentMatchers.anyString;
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

    /*@BeforeEach
    void setUp() {
        //MockitoAnnotations.openMocks(this);
        priceRestController = new PriceControllerAdapter(priceServicePort, priceMapper);
    }*/

    @Test
    void returnPriceDataOK() {
        when(priceServicePort.findByBrandProductBetweenDate(anyString(), anyString(), anyString()))
                .thenReturn(Price.builder().startDate(LocalDateTime.now()).build());
        var result = priceRestController.findByBrandProductBetweenDate("2", "2", "2020-06-14 10:00:00");
        assertNotNull(result);
    }

    @Test
    void returnNumberFormatException() {
        willThrow(new NumberFormatException("exception"))
                .given(priceServicePort).findByBrandProductBetweenDate(anyString(), anyString(),anyString());
        assertThrows(NumberFormatException.class, () -> {
            priceRestController.findByBrandProductBetweenDate("3A", "1", "2020-06-14 10:00:00");
        });
    }

    @Test
    void returnNull() {
        when(priceServicePort.findByBrandProductBetweenDate(anyString(), anyString(), anyString()))
                .thenReturn(null);
        assertNotNull(priceRestController.findByBrandProductBetweenDate("1", "1", ""));
    }
}
