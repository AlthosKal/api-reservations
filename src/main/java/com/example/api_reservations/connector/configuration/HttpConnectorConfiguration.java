package com.example.api_reservations.connector.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
@ConfigurationProperties(prefix = "http-connector")
@Data
public class HttpConnectorConfiguration {

    private HashMap<String, HostConfiguration> hosts;

    @Data
    public static class HostConfiguration {
        private String host;
        private int port;
        private HashMap<String, EndpointConfiguration> endpoints;
    }

    @Data
    public static class EndpointConfiguration {
        private String url;
        private long readTimeout;
        private long writeTimeout;
        private long connectTimeout;
    }
}
