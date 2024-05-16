package com.iex.demo.config;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * @author Paul Ngouabeu
 * This class holds the configuration from properties file.
 */
@Component
@Data
public class ServiceConfig {
    /**
     * The property for the IEX URL
     */
    @Value("${iex.url}")
    public String iexUrl;

    /**
     * The property for the IEX key token
     */
    @Value("${iex.token}")
    public String iexToken;
}
