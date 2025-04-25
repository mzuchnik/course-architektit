package pl.mzuchnik.bookms.infrastructure.api;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mzuchnik.bookms.application.BookManagementService;
import pl.mzuchnik.bookms.domain.domain.Book;
import pl.mzuchnik.bookms.domain.domain.BookId;
import pl.mzuchnik.bookms.domain.domain.Price;
import pl.mzuchnik.bookms.domain.dto.BookNotFoundException;
import pl.mzuchnik.bookms.domain.ports.spi.BookRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/books")
@Slf4j
@RequiredArgsConstructor
class BookApi {

    private final BookManagementService bookManagementService;
    private final BookRepository bookRepository;

    @GetMapping
    List<BookResponse> getAllBooks() {
        return bookRepository.findAll().stream().map(BookResponse::from).toList();
    }

    @GetMapping("/{uuid}")
    ResponseEntity<BookResponse> getBook(@PathVariable(name = "uuid") String uuid) {
        return bookRepository.findById(BookId.create(UUID.fromString(uuid)))
                .map(BookResponse::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{uuid}/author")
    ResponseEntity<Void> changeAuthor(@PathVariable(name = "uuid") String uuid,
                                      @Valid @RequestBody ChangeBookAuthorRequest requestBody) {
        try {
            bookManagementService.changeBookAuthor(BookId.create(UUID.fromString(uuid)), requestBody.author());
            return ResponseEntity.ok().build();
        } catch (BookNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{uuid}/title")
    ResponseEntity<Void> changeTitle(@PathVariable(name = "uuid") String uuid,
                                     @Valid @RequestBody ChangeBookTitleRequest requestBody) {
        try {
            bookManagementService.changeBookTitle(BookId.create(UUID.fromString(uuid)), requestBody.title());
            return ResponseEntity.ok().build();
        } catch (BookNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{uuid}/price")
    ResponseEntity<Void> changePrice(@PathVariable(name = "uuid") String uuid,
                                     @Valid @RequestBody ChangeBookPriceRequest requestBody){
        try{
            bookManagementService.changeBookPrice(BookId.create(UUID.fromString(uuid)), Price.create(BigDecimal.valueOf(requestBody.price())));
            return ResponseEntity.ok().build();
        }catch (BookNotFoundException e){
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{uuid}/price")
    ResponseEntity<BookPriceResponse> getPrice(@PathVariable(name = "uuid") String uuid) {
        try{
            Book book = bookRepository.findById(BookId.create(UUID.fromString(uuid))).orElseThrow(() -> new BookNotFoundException(uuid));
            return ResponseEntity.ok(new BookPriceResponse(book.getPrice().price().doubleValue()));
        }catch (BookNotFoundException e){
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        }
    }
}
