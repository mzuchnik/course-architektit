package pl.mzuchnik.bookms.domain.domain;

import pl.mzuchnik.bookms.domain.dto.BookException;

import java.util.UUID;

public record BookId(UUID uuid) {
    public BookId{
        if(uuid == null){
            throw new BookException("Book UUID is null");
        }
    }

    public static BookId createNew() {
        return new BookId(UUID.randomUUID());
    }

    public static BookId create(UUID uuid) {
        if(uuid == null){
            throw new BookException("Book UUID is null");
        }
        return new BookId(uuid);
    }
}
