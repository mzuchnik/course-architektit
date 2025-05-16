package pl.mzuchnik.commoninstance;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "service")
public record ServiceProperties(String name,
                                String instance) {
}
