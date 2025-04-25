package pl.mzuchnik.bookms.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mzuchnik.bookms.domain.domain.Book;
import pl.mzuchnik.bookms.domain.domain.BookId;
import pl.mzuchnik.bookms.domain.domain.Price;
import pl.mzuchnik.bookms.domain.dto.BookNotFoundException;
import pl.mzuchnik.bookms.domain.ports.api.ChangeBookAuthor;
import pl.mzuchnik.bookms.domain.ports.api.ChangeBookPrice;
import pl.mzuchnik.bookms.domain.ports.api.ChangeBookTitle;
import pl.mzuchnik.bookms.domain.ports.spi.BookRepository;

@Service
public class BookManagementService implements ChangeBookAuthor, ChangeBookTitle, ChangeBookPrice {

    private final BookRepository bookRepository;

    public BookManagementService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional
    public Book changeBookAuthor(BookId bookId, String newAuthor) throws BookNotFoundException {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book with id " + bookId + " not found"));
        book.changeAuthor(newAuthor);
        bookRepository.save(book);
        return book;
    }


    @Override
    @Transactional
    public Book changeBookTitle(BookId bookId, String newTitle) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book with id " + bookId + " not found"));
        book.changeTitle(newTitle);
        bookRepository.save(book);
        return book;
    }

    @Override
    @Transactional
    public Book changeBookPrice(BookId bookId, Price newPrice) throws BookNotFoundException {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book with id " + bookId + " not found"));
        book.changePrice(newPrice);
        bookRepository.save(book);
        return book;
    }
}
