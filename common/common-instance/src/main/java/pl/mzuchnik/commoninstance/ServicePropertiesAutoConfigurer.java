package pl.mzuchnik.commoninstance;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ServiceProperties.class)
class ServicePropertiesAutoConfigurer {

}
