package pl.mzuchnik.commonconfig.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import pl.mzuchnik.commonconfig.properties.ApiGatewayProperties;

@Configuration
@EnableConfigurationProperties({ApiGatewayProperties.class})
class CommonConfig {
}
