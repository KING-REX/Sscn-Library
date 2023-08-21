package com.sscn.library.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sscn.library.config.SpringConfiguration;
import com.sscn.library.exception.NotFoundException;
import com.sscn.library.repository.BookRepository;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Book implements Serializable, Cloneable {
    @Id
    @NotNull(message = "ISBN is mandatory!")
    private String isbn;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDate datePurchased;

    @Column(nullable = false)
    private Integer availableCopies;

    @Column(nullable = false)
    private Integer totalCopies;

    @JsonIgnore
    @ManyToMany(mappedBy = "books")
    private List<Author> authors;

    @JsonProperty("authors")
    @Transient
    private List<String> authorIds;

    //TODO: Do the json workaround for object printing (like how you did for the authors) and make it print an array of bookIssuance ids, not the
    // actual bookIssuance objects!
    @JsonIgnore
    @OneToMany(mappedBy = "bookIssued", cascade = CascadeType.ALL)
    private List<BookIssuance> bookIssuances;

    //TODO: Do the json workaround thing for this attribute too! Then you can remove the jsonIgnore!
    @JsonIgnore
    @OneToMany(mappedBy = "bookReturned", cascade = CascadeType.ALL)
    private List<BookReturns> bookReturns;

    public Book(String isbn, String title, LocalDate datePurchased, Integer availableCopies, Integer totalCopies) {
        this.isbn = isbn;
        this.title = title;
        this.datePurchased = datePurchased;
        this.availableCopies = availableCopies;
        this.totalCopies = totalCopies;
        this.authors = new ArrayList<>();
        this.authorIds = new ArrayList<>();
    }

    public Book(String isbn, String title, LocalDate datePurchased, Integer availableCopies, Integer totalCopies, List<Author> authors) {
        this.isbn = isbn;
        this.title = title;
        this.datePurchased = datePurchased;
        this.availableCopies = availableCopies;
        this.totalCopies = totalCopies;
        this.authors = authors;
        this.authorIds = new ArrayList<>();
        authors.forEach((author) -> this.authorIds.add(author.getId().toString()));
    }

    public Book() {
        this.authors = new ArrayList<>();
        this.authorIds = new ArrayList<>();
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

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
        authors.forEach((author) -> this.authorIds.add(author.getId().toString()));
    }

    public void fillInAuthorIds() {
        this.authors.forEach((author) -> this.authorIds.add(author.getId().toString()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (!isbn.equals(book.isbn)) return false;
        if (!title.equals(book.title)) return false;
        if (!datePurchased.equals(book.datePurchased)) return false;
        if (!availableCopies.equals(book.availableCopies)) return false;
        if (!totalCopies.equals(book.totalCopies)) return false;
        if (!authors.equals(book.authors)) return false;
        return authorIds.equals(book.authorIds);
    }

    @Override
    public int hashCode() {
        int result = isbn.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + datePurchased.hashCode();
        result = 31 * result + availableCopies.hashCode();
        result = 31 * result + totalCopies.hashCode();
        result = 31 * result + authors.hashCode();
        result = 31 * result + authorIds.hashCode();
        return result;
    }

    public static Book of(String isbn) {
        BookRepository bookRepository = (BookRepository) SpringConfiguration.contextProvider().getApplicationContext().getBean("bookRepository");

        return bookRepository.findByIsbn(isbn).orElseThrow(() -> new NotFoundException(String.format("No book with isbn %s exists.", isbn)));
    }

//    @JsonCreator
//    public static Book createBook(@JsonProperty("isbn") String isbn, @JsonProperty("title") String title,
//                                  @JsonProperty("datePurchased") LocalDate datePurchased, @JsonProperty("availableCopies") Integer availableCopies,
//                                  @JsonProperty("totalCopies") Integer totalCopies, @JsonProperty("authors") List<Author> authors) {
//
//        return new Book(isbn, title, datePurchased, availableCopies, totalCopies, authors);
//    }

//    @JsonProperty("authorIds")
//    public List<Author> deserializeAuthors(List<Integer> authors) {
//            List<Author> authorList = authors.stream().map(Author::of).toList();
//            authorList.forEach(System.out::println);
//            return authorList;
//    }

    @JsonProperty("authors")
    public List<String> getAuthorIds() {
        this.fillInAuthorIds();
        return this.authorIds;
    }

    @Override
    public Book clone() throws CloneNotSupportedException {
        return (Book) super.clone();
    }
}
