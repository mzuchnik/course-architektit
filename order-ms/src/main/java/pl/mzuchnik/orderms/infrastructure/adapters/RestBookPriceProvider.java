package pl.mzuchnik.orderms.infrastructure.adapters;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import pl.mzuchnik.commonconfig.properties.ApiGatewayProperties;
import pl.mzuchnik.orderms.application.BookPriceProvider;
import pl.mzuchnik.orderms.domain.BookId;
import pl.mzuchnik.orderms.domain.Price;

import java.math.BigDecimal;
import java.util.Optional;

@Service
class RestBookPriceProvider implements BookPriceProvider {

    private final RestClient restClient;
    private static final String BOOK_PRICE_ENDPOINT = "/api/v1/books/{bookId}/price";

    RestBookPriceProvider(RestClient.Builder restClientBuilder,
                          ApiGatewayProperties apiGatewayProperties) {
        this.restClient = restClientBuilder.baseUrl(apiGatewayProperties.url()).build();
    }

    @Override
    @CircuitBreaker(name = "getBookPrice", fallbackMethod = "fallbackGetBookPrice")
    public Optional<Price> getBookPrice(BookId bookId) {

        ResponseEntity<BookPriceResponse> response = callForBookPrice(bookId);
        if(response.getStatusCode().is4xxClientError()) {
            return Optional.empty();
        }
        if(response.getStatusCode().is5xxServerError()) {
            throw new RuntimeException("Error getting price from server");
        }

        return Optional.of(new Price(BigDecimal.valueOf(response.getBody().price())));
    }


    @CircuitBreaker(name = "callForBookPrice", fallbackMethod = "fallbackCallForBookPrice")
    @CacheEvict()
    private ResponseEntity<BookPriceResponse> callForBookPrice(BookId bookId) {
        return restClient.get()
                .uri(BOOK_PRICE_ENDPOINT, bookId.uuid())
                .retrieve()
                .toEntity(BookPriceResponse.class);
    }

    private ResponseEntity<BookPriceResponse> fallbackCallForBookPrice(BookId bookId) {

    }

    record BookPriceResponse(double price) {
    }
}
