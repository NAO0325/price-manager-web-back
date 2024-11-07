package com.price.manager.back.driving.controllers.error;


import com.price.manager.back.application.exceptions.PriceNotFoundException;
import com.price.manager.back.application.ports.driving.PriceServicePort;
import com.price.manager.back.driving.controllers.adapters.PriceControllerAdapter;
import com.price.manager.back.driving.controllers.mappers.PriceMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDateTime;

import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest({PriceControllerAdapter.class, CustomExceptionHandler.class})
@ContextConfiguration(classes = {PriceControllerAdapter.class, CustomExceptionHandler.class})
class CustomExceptionHandlerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PriceServicePort priceServicePort;

    @MockBean
    private PriceMapper mapper;


    @Test
    void whenMissingRequiredParameter_thenReturns400() throws Exception {
        mvc.perform(get("/api/catalogue/price/findByBrandProductBetweenDate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("productId", "35455")
                        .param("brandId", "1"))  // dateQuery parameter is missing
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Missing Required Parameter"))
                .andExpect(jsonPath("$.message").value("Required parameter 'dateQuery' of type 'LocalDateTime' is missing"));
    }

    @Test
    void whenInvalidDateFormat_thenReturns400() throws Exception {
        mvc.perform(get("/api/catalogue/price/findByBrandProductBetweenDate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("dateQuery", "2020-10-10")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Parameter Type Mismatch"))
                .andExpect(jsonPath("$.message").value("Invalid format for parameter 'dateQuery': The value '2020-10-10' is not valid"));
    }

    @Test
    void whenInvalidProductId_thenReturns400() throws Exception {
        mvc.perform(get("/api/catalogue/price/findByBrandProductBetweenDate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("dateQuery", "2020-06-14T10:00:00")
                        .param("productId", "invalid-product")
                        .param("brandId", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Parameter Type Mismatch"))
                .andExpect(jsonPath("$.message").value("Invalid format for parameter 'productId': The value 'invalid-product' is not valid"));
    }

    @Test
    void whenInvalidBrandId_thenReturns400() throws Exception {
        mvc.perform(get("/api/catalogue/price/findByBrandProductBetweenDate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("dateQuery", "2020-06-14T10:00:00")
                        .param("productId", "35455")
                        .param("brandId", "invalid-brand"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Parameter Type Mismatch"))
                .andExpect(jsonPath("$.message").value("Invalid format for parameter 'brandId': The value 'invalid-brand' is not valid"));
    }

    @Test
    void whenPriceNotFound_thenReturns404() throws Exception {
        var dateTest = LocalDateTime.of(2020,6,14, 10, 0, 0);

        willThrow(new PriceNotFoundException(1L, 35455L, dateTest))
                .given(priceServicePort).findByBrandProductBetweenDate(anyLong(), anyLong(), any());

        mvc.perform(get("/api/catalogue/price/findByBrandProductBetweenDate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("dateQuery", "2020-06-14T10:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Price Not Found"))
                .andExpect(jsonPath("$.message").value("Price not found for brand ID: 1, product ID: 35455, date: 2020-06-14T10:00"));
    }

    @Test
    void whenUnexpectedError_thenReturns500() throws Exception {
        when(priceServicePort.findByBrandProductBetweenDate(anyLong(), anyLong(), any()))
                .thenThrow(new RuntimeException("Unexpected error"));

        mvc.perform(get("/api/catalogue/price/findByBrandProductBetweenDate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("dateQuery", "2020-06-14T10:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.error").value("Internal Server Error"))
                .andExpect(jsonPath("$.message").value("An unexpected error occurred"));
    }
}