package pl.mzuchnik.orderms.infrastructure.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.web.client.RestClientBuilderConfigurer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.client.OAuth2ClientHttpRequestInterceptor;
import org.springframework.security.oauth2.client.web.client.RequestAttributePrincipalResolver;
import org.springframework.web.client.RestClient;
import pl.mzuchnik.orderms.infrastructure.properties.ApiEndpointProperties;

@Configuration
@EnableConfigurationProperties(value = ApiEndpointProperties.class)
class AppConfig {


    @Bean
    @LoadBalanced
    RestClient.Builder restClientBuilder(RestClientBuilderConfigurer restClientBuilderConfigurer,
                                         @Qualifier("logbookClientHttpRequestInterceptor") ClientHttpRequestInterceptor clientHttpRequestInterceptor,
                                         OAuth2AuthorizedClientManager authorizedClientManager) {

        OAuth2ClientHttpRequestInterceptor interceptor =
                new OAuth2ClientHttpRequestInterceptor(authorizedClientManager);
        interceptor.setClientRegistrationIdResolver(resolver -> "keycloak-order-ms");
        interceptor.setPrincipalResolver(new RequestAttributePrincipalResolver());

        return restClientBuilderConfigurer.configure(RestClient.builder())
                .requestInterceptor(clientHttpRequestInterceptor)
                .requestInterceptor(interceptor);
    }
}
