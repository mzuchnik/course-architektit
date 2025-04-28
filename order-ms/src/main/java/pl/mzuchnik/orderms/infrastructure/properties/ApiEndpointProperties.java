package pl.mzuchnik.orderms.infrastructure.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "order.api.endpoints")
public record ApiEndpointProperties(
        String bookPriceEndpoint
) {
}
