package com.sscn.library.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private int availableCopies;

    @Column(nullable = false)
    private int totalCopies;

    @ManyToMany(mappedBy = "books", cascade = CascadeType.ALL)
    private List<Author> authors;

    public Book(String isbn, String title, LocalDate datePurchased, int availableCopies, int totalCopies) {
        this.isbn = isbn;
        this.title = title;
        this.datePurchased = datePurchased;
        this.availableCopies = availableCopies;
        this.totalCopies = totalCopies;
        this.authors = new ArrayList<>();
    }

    public Book(String isbn, String title, LocalDate datePurchased, int availableCopies, int totalCopies, List<Author> authors) {
        this.isbn = isbn;
        this.title = title;
        this.datePurchased = datePurchased;
        this.availableCopies = availableCopies;
        this.totalCopies = totalCopies;
        this.authors = authors;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDatePurchased() {
        return datePurchased;
    }

    public void setDatePurchased(LocalDate datePurchased) {
        this.datePurchased = datePurchased;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }
}
