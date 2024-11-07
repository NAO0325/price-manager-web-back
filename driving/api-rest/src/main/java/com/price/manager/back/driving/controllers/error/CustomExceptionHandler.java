package com.price.manager.back.driving.controllers.error;

import com.price.manager.back.application.exceptions.PriceNotFoundException;
import com.price.manager.back.driving.controllers.models.ErrorResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


@ControllerAdvice
public class CustomExceptionHandler {

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "400",
                    description = "Required parameter.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    public ResponseEntity<ErrorResponse> handleMissingParameter(MissingServletRequestParameterException ex) {
        var message = String.format(
                "Required parameter '%s' of type '%s' is missing",
                ex.getParameterName(),
                ex.getParameterType()
        );

        var errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Missing Required Parameter",
                message
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "404",
                    description = "Invalid format for parameter.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        var paramName = ex.getName();
        var invalidValue = ex.getValue() != null ? ex.getValue().toString() : "null";

        var message = String.format(
                "Invalid format for parameter '%s': The value '%s' is not valid",
                paramName, invalidValue
        );

        var errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Parameter Type Mismatch",
                message
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "404",
                    description = "Price not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @ExceptionHandler(value = {PriceNotFoundException.class})
    public ResponseEntity<ErrorResponse> handlePriceNotFoundException(PriceNotFoundException ex) {

        var errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Price Not Found",
                ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        var errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "An unexpected error occurred"
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }
}
