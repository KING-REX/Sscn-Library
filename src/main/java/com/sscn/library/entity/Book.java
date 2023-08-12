package com.sscn.library.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
public class Book implements Serializable {
    @Id
    private String isbn;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDate datePurchased;

    @Column(nullable = false)
    private Integer availableCopies;

    @Column(nullable = false)
    private Integer totalCopies;

    @ManyToMany(mappedBy = "books", cascade = CascadeType.ALL)
    private List<Author> authors;

    public Book(String isbn, String title, LocalDate datePurchased, Integer availableCopies, Integer totalCopies) {
        this.isbn = isbn;
        this.title = title;
        this.datePurchased = datePurchased;
        this.availableCopies = availableCopies;
        this.totalCopies = totalCopies;
        this.authors = new ArrayList<>();
    }

    public Book(String isbn, String title, LocalDate datePurchased, Integer availableCopies, Integer totalCopies, List<Author> authors) {
        this.isbn = isbn;
        this.title = title;
        this.datePurchased = datePurchased;
        this.availableCopies = availableCopies;
        this.totalCopies = totalCopies;
        this.authors = authors;
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", datePurchased=" + datePurchased +
                ", availableCopies=" + availableCopies +
                ", totalCopies=" + totalCopies +
                '}';
    }
}
