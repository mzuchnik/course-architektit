package pl.mzuchnik.orderms.infrastructure.adapters;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import pl.mzuchnik.orderms.application.BookPriceProvider;
import pl.mzuchnik.orderms.domain.BookId;
import pl.mzuchnik.orderms.domain.Price;

import java.math.BigDecimal;
import java.util.Optional;

@Service
class RestBookPriceProvider implements BookPriceProvider {

    private final RestClient restClient;
    private static final String BOOK_PRICE_ENDPOINT = "http://localhost:8080/api/v1/books/{bookId}/price";

    RestBookPriceProvider(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
    }


    @Override
    public Optional<Price> getBookPrice(BookId bookId) {

        ResponseEntity<BookPriceResponse> response = restClient.get()
                .uri(BOOK_PRICE_ENDPOINT, bookId.uuid())
                .retrieve()
                .toEntity(BookPriceResponse.class);
        if(response.getStatusCode().is4xxClientError()) {
            return Optional.empty();
        }
        if(response.getStatusCode().is5xxServerError()) {
            throw new RuntimeException("Error getting price from server");
        }

        return Optional.of(new Price(BigDecimal.valueOf(response.getBody().price())));
    }

    record BookPriceResponse(double price) {
    }
}
