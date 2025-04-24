package pl.mzuchnik.bookms.domain.domain;

public class BookFactory {

    public static Book createNewBook(String title, String author){
        return new Book(BookId.createNew(), title, author);
    }

    public static Book create(BookId bookId, String title, String author){
        return new Book(bookId, title, author);
    }
}
