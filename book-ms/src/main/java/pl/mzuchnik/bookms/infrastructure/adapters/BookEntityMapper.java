package pl.mzuchnik.bookms.infrastructure.adapters;

import pl.mzuchnik.bookms.domain.domain.Book;
import pl.mzuchnik.bookms.domain.domain.BookFactory;
import pl.mzuchnik.bookms.domain.domain.BookId;

class BookEntityMapper {

    static BookJpaEntity toJpaEntity(Book book) {
        BookJpaEntity bookJpaEntity = new BookJpaEntity();
        bookJpaEntity.setUuid(book.getId().uuid());
        bookJpaEntity.setTitle(book.getTitle());
        bookJpaEntity.setAuthor(book.getAuthor());

        return bookJpaEntity;
    }

    static Book toDomainEntity(BookJpaEntity bookJpaEntity) {
        return BookFactory.create(BookId.create(bookJpaEntity.getUuid()), bookJpaEntity.getTitle(), bookJpaEntity.getAuthor());
    }
}
