package pl.mzuchnik.bookms.domain.domain;

import pl.mzuchnik.bookms.domain.dto.BookException;

public class Book {

    private BookId id;

    private String title;

    private String author;

    Book(BookId id, String title, String author) {
        validate(id, title, author);
        this.id = id;
        this.title = title;
        this.author = author;
    }

    private void validate(BookId id, String title, String author) {
        if(id == null){
            throw new BookException("BookId is null");
        }
        if(title == null || title.isEmpty()){
            throw new BookException("Title is null or empty");
        }
        if(author == null || author.isEmpty()){
            throw new BookException("Author is null or empty");
        }
    }

    public void changeTitle(String newTitle) {
        if(newTitle == null || newTitle.isEmpty()){
            throw new BookException("New title is null or empty");
        }
        this.title = newTitle;
    }

    public void changeAuthor(String newAuthor) {
        if(newAuthor == null || newAuthor.isEmpty()){
            throw new BookException("New author is null or empty");
        }
        this.author = newAuthor;
    }

    public BookId getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
}
