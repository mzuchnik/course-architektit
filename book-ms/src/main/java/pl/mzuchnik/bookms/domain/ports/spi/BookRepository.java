package pl.mzuchnik.bookms.domain.ports.spi;

import pl.mzuchnik.bookms.domain.domain.Book;
import pl.mzuchnik.bookms.domain.domain.BookId;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Book save(Book book);
    Optional<Book> findById(BookId bookId);
    List<Book> findByAuthor(String author);
    List<Book> findAll();
}
