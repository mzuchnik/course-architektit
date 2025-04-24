package pl.mzuchnik.bookms.infrastructure.api;

import pl.mzuchnik.bookms.domain.domain.Book;

record BookResponse(String uuid, String title, String author) {

    static BookResponse from(Book book) {
        return new BookResponse(book.getId().uuid().toString(), book.getTitle(), book.getAuthor());
    }
}
