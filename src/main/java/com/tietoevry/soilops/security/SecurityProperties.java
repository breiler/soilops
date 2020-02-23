package com.tietoevry.soilops.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "app.security")
public class SecurityProperties {

    /**
     * A text string to be used for signing the JWT token
     */
    private String tokenSecret;

    /**
     * The number of milliseconds for the token to live
     */
    private long tokenTimeToLive;

    /**
     * The number of milliseconds for the short token to live (to be used for session transferring)
     */
    private long tokenShortTimeToLive;
}