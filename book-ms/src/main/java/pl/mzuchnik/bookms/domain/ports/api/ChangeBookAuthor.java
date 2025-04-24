package pl.mzuchnik.bookms.domain.ports.api;

import pl.mzuchnik.bookms.domain.domain.Book;
import pl.mzuchnik.bookms.domain.domain.BookId;
import pl.mzuchnik.bookms.domain.dto.BookNotFoundException;

public interface ChangeBookAuthor {

    Book changeBookAuthor(BookId bookId, String newAuthor) throws BookNotFoundException;
}
