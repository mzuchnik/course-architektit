package pl.mzuchnik.orderms.infrastructure.adapters;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import pl.mzuchnik.commonconfig.properties.ApiGatewayProperties;
import pl.mzuchnik.orderms.application.BookPriceProvider;
import pl.mzuchnik.orderms.domain.BookId;
import pl.mzuchnik.orderms.domain.OrderException;
import pl.mzuchnik.orderms.domain.Price;

import java.math.BigDecimal;

@Service
@Slf4j
class RestBookPriceProvider implements BookPriceProvider {

    private final RestClient restClient;
    private final CacheManager cacheManager;
    private static final String BOOK_PRICE_ENDPOINT = "/api/v1/books/{bookId}/price";
    private static final String LAST_BOOK_PRICE_CACHE_NAME = "lastBookPrice";

    RestBookPriceProvider(RestClient.Builder restClientBuilder,
                          ApiGatewayProperties apiGatewayProperties,
                          CacheManager cacheManager) {
        this.restClient = restClientBuilder.baseUrl(apiGatewayProperties.url()).build();
        this.cacheManager = cacheManager;
    }

    @Override
    @CircuitBreaker(name = "callForBookPrice", fallbackMethod = "fallbackCallForBookPrice")
    @CachePut(cacheNames = LAST_BOOK_PRICE_CACHE_NAME, key = "#bookId.uuid().toString()")
    public Price getBookPrice(BookId bookId) {
        BookPriceResponse bookPrice = callForBookPrice(bookId);

        return new Price(BigDecimal.valueOf(bookPrice.price()));
    }


    private BookPriceResponse callForBookPrice(BookId bookId) {
        return restClient.get()
                .uri(BOOK_PRICE_ENDPOINT, bookId.uuid())
                .retrieve()
                .body(BookPriceResponse.class);
    }

    public Price fallbackCallForBookPrice(BookId bookId, Throwable throwable) {
        log.error("Fallback call for book price {}", bookId, throwable);
        log.error("Trying to get last book price {} from cache", bookId, throwable);
        Price price = cacheManager.getCache(LAST_BOOK_PRICE_CACHE_NAME).get(bookId.uuid().toString(), Price.class);
        if(price != null) {
            return price;
        }
        throw new OrderException("Cannot resolve last book price for book " + bookId);
    }

    record BookPriceResponse(double price) {
    }
}
