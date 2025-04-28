package pl.mzuchnik.commonconfig.properties;

import jakarta.validation.constraints.Pattern;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "api.gateway")
public record ApiGatewayProperties(
        @Pattern(regexp = "^https?://.*", message = "Invalid url format")
        String url) {
}
