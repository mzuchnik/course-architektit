package pl.mzuchnik.orderms.application;

import pl.mzuchnik.orderms.domain.BookId;
import pl.mzuchnik.orderms.domain.Price;

import java.util.Optional;


public interface BookPriceProvider {

    Optional<Price> getBookPrice(BookId bookId);
}
