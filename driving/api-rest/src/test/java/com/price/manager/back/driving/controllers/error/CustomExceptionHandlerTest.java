package com.price.manager.back.driving.controllers.error;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomExceptionHandlerTest {

    @InjectMocks
    private CustomExceptionHandler exceptionHandler;

    @Mock
    private WebRequest webRequest;

    @Mock
    private HandlerMethod handlerMethod;

    private Method method;


    @RestController
    static class PriceControllerAdapter {
        public void testMethod() {
        }
    }

    @BeforeEach
    void setUp() {
        try {
            method = PriceControllerAdapter.class.getDeclaredMethod("testMethod");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void handleAllExceptions_ShouldReturnInternalServerError() {
        // Arrange
        var errorMessage = "Test error message";
        var exception = new Exception(errorMessage);

        // Act
        when(handlerMethod.getMethod()).thenReturn(method);
        var responseEntity = exceptionHandler.handleAllExceptions(
                exception, handlerMethod, webRequest);
        var errorResponse = responseEntity.getBody();

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertNotNull(errorResponse);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorResponse.getStatus());
        assertEquals(PriceControllerAdapter.class.getName(), errorResponse.getError());
        assertEquals(errorMessage, errorResponse.getMessage());

        // Verify mock interactions
        verify(handlerMethod, times(1)).getMethod();
    }

    @Test
    void handleFormat_ShouldReturnBadRequest() {
        // Arrange
        var errorMessage = "Invalid number format";
        var exception = new NumberFormatException(errorMessage);

        // Act
        var responseEntity = exceptionHandler.handleFormat(exception, webRequest);
        var errorResponse = responseEntity.getBody();

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(errorResponse);
        assertEquals(HttpStatus.BAD_REQUEST.value(), errorResponse.getStatus());
        assertEquals("Invalid format error", errorResponse.getError());
        assertEquals(errorMessage, errorMessage);
    }
}