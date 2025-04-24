package pl.mzuchnik.bookms.domain.ports.api;

import pl.mzuchnik.bookms.domain.domain.Book;
import pl.mzuchnik.bookms.domain.domain.BookId;

public interface ChangeBookTitle {

    Book changeBookTitle(BookId bookId, String newTitle);
}
