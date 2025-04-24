package pl.mzuchnik.bookms.infrastructure.adapters;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.mzuchnik.bookms.domain.domain.Book;
import pl.mzuchnik.bookms.domain.domain.BookId;
import pl.mzuchnik.bookms.domain.ports.spi.BookRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
class BookRepositoryAdapter implements BookRepository {

    private final BookJPARepository bookJPARepository;

    @Override
    public Book save(Book book) {
        bookJPARepository.save(BookEntityMapper.toJpaEntity(book));
        return book;
    }

    @Override
    public Optional<Book> findById(BookId bookId) {
        return bookJPARepository.findById(bookId.uuid())
                .map(BookEntityMapper::toDomainEntity);
    }

    @Override
    public List<Book> findByAuthor(String author) {
        return bookJPARepository.findByAuthorIgnoreCase(author)
                .stream()
                .map(BookEntityMapper::toDomainEntity)
                .toList();
    }

    @Override
    public List<Book> findAll() {
        return bookJPARepository.findAll()
                .stream()
                .map(BookEntityMapper::toDomainEntity)
                .toList();
    }
}
