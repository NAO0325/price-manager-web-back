package com.price.manager.back.driving.controllers.adapters;

import com.price.manager.back.application.ports.driving.PriceServicePort;
import com.price.manager.back.domain.Price;
import com.price.manager.back.driving.controllers.mappers.PriceMapper;
import com.price.manager.back.driving.controllers.models.PriceResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WebMvcTest(PriceControllerAdapter.class)
@ContextConfiguration(classes = {PriceControllerAdapter.class})
public class PriceControllerAdapterMvcTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PriceMapper priceMapper;

    @MockBean
    private PriceServicePort priceServicePort;

    @Test
    void returnGetMvcPriceDataOK() throws Exception {

        // when
        when(priceServicePort.findByBrandProductBetweenDate(anyString(), anyString(), anyString()))
                .thenReturn(Price.builder().startDate(LocalDateTime.now()).build());

        when(priceMapper.toResponseDto(any()))
                .thenReturn(PriceResponse.builder().build());

        // then
        mvc.perform(get("/api/catalogue/price/findByBrandProductBetweenDate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("dateQuery", "2020-06-14")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(priceMapper, times(1)).toResponseDto(any());
        verify(priceServicePort, times(1)).findByBrandProductBetweenDate(anyString(), anyString(), anyString());

    }

    @Test
    void returnGetMvcPriceDataNotFound() throws Exception {

        // when
        when(priceServicePort.findByBrandProductBetweenDate(anyString(), anyString(), anyString()))
                .thenReturn(null);

        when(priceMapper.toResponseDto(any()))
                .thenReturn(null);

        // then
        mvc.perform(get("/api/catalogue/price/findByBrandProductBetweenDate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("dateQuery", "2020-06-14")
                        .param("productId", "35455")
                        .param("brandId", "5"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(priceMapper, times(1)).toResponseDto(any());
        verify(priceServicePort, times(1)).findByBrandProductBetweenDate(anyString(), anyString(), anyString());

    }

}
