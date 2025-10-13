package pl.mzuchnik.bookms.infrastructure.adapters;



import io.micrometer.tracing.Tracer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.mzuchnik.bookms.domain.ports.spi.BookRepository;

@Configuration
class BookRepositoryConfig {

    @Bean
    BookRepository bookRepository(BookJPARepository bookJPARepository, Tracer tracer) {
        return new TraceableBookRepositoryDecorator(new BookRepositoryAdapter(bookJPARepository), tracer);
    }
}
