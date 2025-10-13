package pl.mzuchnik.bookms.infrastructure.adapters;


import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import pl.mzuchnik.bookms.domain.domain.Book;
import pl.mzuchnik.bookms.domain.domain.BookId;
import pl.mzuchnik.bookms.domain.ports.spi.BookRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
class TraceableBookRepositoryDecorator implements BookRepository {

    private final BookRepository bookRepository;
    private final Tracer tracer;

    @Override
    public Book save(Book book) {
        Span span = tracer.nextSpan().name("BookRepository.save").start();
        try (var ws = tracer.withSpan(span)) {
            span.tag("bookId", book.getId() != null ? book.getId().uuid().toString() : "new");
            span.tag("entity.type", "book");
            span.tag("operation", "save");

            Book result = bookRepository.save(book);

            span.tag("result", "success");
            return result;
        } catch (RuntimeException e) {
            span.tag("error", "true");
            span.error(e);
            throw e;
        } finally {
            span.end();
        }
    }

    @Override
    public Optional<Book> findById(BookId bookId) {
        Span span = tracer.nextSpan().name("BookRepository.findById").start();
        try (var ws = tracer.withSpan(span)) {
            span.tag("bookId", bookId.uuid().toString());
            span.tag("entity.type", "book");
            span.tag("operation", "findById");
            Optional<Book> result = bookRepository.findById(bookId);
            span.tag("result", result.isPresent() ? "found" : "not found");
            return result;
        } catch (RuntimeException e) {
            span.tag("error", "true");
            span.error(e);
            throw e;
        } finally {
            span.end();
        }
    }

    @Override
    public List<Book> findByAuthor(String author) {
        Span span = tracer.nextSpan().name("BookRepository.findByAuthor").start();
        try (var ws = tracer.withSpan(span)) {
            span.tag("entity.type", "book");
            span.tag("author", author);
            span.tag("operation", "findByAuthor");

            List<Book> result = bookRepository.findByAuthor(author);

            span.tag("result.count", String.valueOf(result.size()));
            return result;
        } catch (RuntimeException e) {
            span.tag("error", "true");
            span.error(e);
            throw e;
        } finally {
            span.end();
        }
    }

    @Override
    public List<Book> findAll() {

        Span span = tracer.nextSpan().name("BookRepository.findAll").start();
        try (var ws = tracer.withSpan(span)) {
            span.tag("entity.type", "book");
            span.tag("operation", "findAll");

            List<Book> result = bookRepository.findAll();

            span.tag("result.count", String.valueOf(result.size()));
            return result;
        } catch (RuntimeException e) {
            span.tag("error", "true");
            span.error(e);
            throw e;
        } finally {
            span.end();
        }
    }
}
