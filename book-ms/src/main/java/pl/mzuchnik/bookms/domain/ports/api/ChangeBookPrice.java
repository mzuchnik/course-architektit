package pl.mzuchnik.bookms.domain.ports.api;

import pl.mzuchnik.bookms.domain.domain.Book;
import pl.mzuchnik.bookms.domain.domain.BookId;
import pl.mzuchnik.bookms.domain.domain.Price;
import pl.mzuchnik.bookms.domain.dto.BookNotFoundException;

public interface ChangeBookPrice {

    Book changeBookPrice(BookId bookId, Price newPrice) throws BookNotFoundException;
}
