package com.price.manager.back.driving.controllers.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import io.swagger.v3.oas.models.servers.Server;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OpenApiConfigTest {

    @InjectMocks
    private OpenApiConfig openApiConfig;

    @Test
    void customOpenAPI_ShouldReturnValidOpenAPIConfiguration() {
        // Arrange
        var testPort = "8080";
        ReflectionTestUtils.setField(openApiConfig, "serverPort", testPort);

        // Act
        var result = openApiConfig.customOpenAPI();

        // Assert
        assertNotNull(result, "OpenAPI configuration should not be null");

        // Verify Server configuration
        assertFalse(result.getServers().isEmpty(), "Servers list should not be empty");
        Server server = result.getServers().get(0);
        assertEquals("http://localhost:" + testPort, server.getUrl(),
                "Server URL should match expected configuration");

        // Verify Info configuration
        assertNotNull(result.getInfo(), "API Info should not be null");
        assertEquals("Price Manager API", result.getInfo().getTitle(),
                "API title should match expected value");
        assertEquals("1.0.0", result.getInfo().getVersion(),
                "API version should match expected value");
        assertEquals("Price Management System API Documentation",
                result.getInfo().getDescription(),
                "API description should match expected value");

        // Verify Contact information
        assertNotNull(result.getInfo().getContact(), "Contact information should not be null");
        assertEquals("Napoleon Avila Ochoa", result.getInfo().getContact().getName(),
                "Contact name should match expected value");
        assertEquals("napoavi@gmail.com", result.getInfo().getContact().getEmail(),
                "Contact email should match expected value");
    }

    @Test
    void customOpenAPI_ShouldHandleEmptyPort() {
        // Arrange
        ReflectionTestUtils.setField(openApiConfig, "serverPort", "");

        // Act
        var result = openApiConfig.customOpenAPI();

        // Assert
        assertNotNull(result, "OpenAPI configuration should not be null even with empty port");
        assertFalse(result.getServers().isEmpty(), "Servers list should not be empty");
        assertEquals("http://localhost:", result.getServers().get(0).getUrl(),
                "Server URL should handle empty port gracefully");
    }

    @Test
    void customOpenAPI_ShouldHandleNullPort() {
        // Arrange
        ReflectionTestUtils.setField(openApiConfig, "serverPort", null);

        // Act
        var result = openApiConfig.customOpenAPI();

        // Assert
        assertNotNull(result, "OpenAPI configuration should not be null even with null port");
        assertFalse(result.getServers().isEmpty(), "Servers list should not be empty");
        assertEquals("http://localhost:null", result.getServers().get(0).getUrl(),
                "Server URL should handle null port gracefully");
    }
}