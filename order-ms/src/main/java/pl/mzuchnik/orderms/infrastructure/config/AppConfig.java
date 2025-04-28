package pl.mzuchnik.orderms.infrastructure.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import pl.mzuchnik.orderms.infrastructure.properties.ApiEndpointProperties;

@Configuration
@EnableConfigurationProperties(value = ApiEndpointProperties.class)
class AppConfig {
}
