package pl.mzuchnik.bookms.domain.domain;

import pl.mzuchnik.bookms.domain.dto.BookException;

public class Book {

    private BookId id;

    private String title;

    private String author;

    private Price price;

    Book(BookId id, String title, String author, Price price) {
        validate(id, title, author, price);
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
    }

    private void validate(BookId id, String title, String author, Price price) {
        if(id == null){
            throw new BookException("BookId is null");
        }
        if(title == null || title.isEmpty()){
            throw new BookException("Title is null or empty");
        }
        if(author == null || author.isEmpty()){
            throw new BookException("Author is null or empty");
        }
        if(price == null){
            throw new BookException("Price is null");
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

    public void changePrice(Price newPrice) {
        if(newPrice == null){
            throw new BookException("New price is null");
        }
        this.price = newPrice;
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

    public Price getPrice() {
        return price;
    }
}
