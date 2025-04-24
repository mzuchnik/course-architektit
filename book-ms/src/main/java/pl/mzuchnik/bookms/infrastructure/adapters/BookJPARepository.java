package pl.mzuchnik.bookms.infrastructure.adapters;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

interface BookJPARepository extends JpaRepository<BookJpaEntity, UUID> {

    List<BookJpaEntity> findByAuthorIgnoreCase(String author);
}
