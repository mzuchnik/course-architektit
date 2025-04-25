package pl.mzuchnik.bookms.infrastructure.adapters;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "books")
class BookJpaEntity {

    @Id
    private UUID uuid;

    @Column(nullable = false, name = "title")
    private String title;

    @Column(nullable = false, name = "author")
    private String author;

    @Column(nullable = false, name = "price")
    private Double price;

}
