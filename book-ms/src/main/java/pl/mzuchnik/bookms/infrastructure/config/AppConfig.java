package pl.mzuchnik.bookms.infrastructure.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.web.client.RestClientBuilderConfigurer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestClient;

@Configuration
class AppConfig {

    @Bean
    @LoadBalanced
    RestClient.Builder restClientBuilder(RestClientBuilderConfigurer restClientBuilderConfigurer, @Qualifier("logbookClientHttpRequestInterceptor") ClientHttpRequestInterceptor clientHttpRequestInterceptor) {
        return restClientBuilderConfigurer.configure(RestClient.builder()).requestInterceptor(clientHttpRequestInterceptor);
    }
}
