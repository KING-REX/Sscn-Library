package com.sscn.library.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sscn.library.config.SpringConfiguration;
import com.sscn.library.exception.InvalidArgumentException;
import com.sscn.library.exception.NotFoundException;
import com.sscn.library.repository.AuthorRepository;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@Entity
public class Author implements Serializable, Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @JsonIgnore
    @ManyToMany
//    @JoinTable(name = "authors_books",joinColumns = {@JoinColumn(name = "author_id")},inverseJoinColumns = {@JoinColumn(name = "book_id")})
    private List<Book> books;

    @JsonProperty("books")
    @Transient
    private List<String> bookIsbns;

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.books = new ArrayList<>();
        this.bookIsbns = new ArrayList<>();
    }

    public Author(String firstName, String lastName, List<Book> books) {
        this.books = books;
        books.forEach((book) -> this.bookIsbns.add(book.getIsbn()));
    }

    public Author() {
        this.books = new ArrayList<>();
        this.bookIsbns = new ArrayList<>();
    }

    public void setBooks(List<Book> books) {
        this.books = books;
        books.forEach((book) -> this.bookIsbns.add(book.getIsbn()));
    }

    public void fillInBookIsbns() {
        this.books.forEach((book) -> this.bookIsbns.add(book.getIsbn()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Author author = (Author) o;

        if (!id.equals(author.id)) return false;
        if (!firstName.equals(author.firstName)) return false;
        if (!lastName.equals(author.lastName)) return false;
        if (!books.equals(author.books)) return false;
        return bookIsbns.equals(author.bookIsbns);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + books.hashCode();
        result = 31 * result + bookIsbns.hashCode();
        return result;
    }

    public static Author of(Integer id) {
        AuthorRepository authorRepository = (AuthorRepository) SpringConfiguration.contextProvider().getApplicationContext().getBean("authorRepository");

        return authorRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("No Author with isbn %s exists.", id)));
    }

//    @JsonProperty("books")
//    public List<Book> deserializeBooks(List<String> books) {
//        List<Book> bookList = books.stream().map(Book::of).toList();
//        bookList.forEach(System.out::println);
//        return bookList;
//    }

//    @JsonProperty("books")
//    public <T> List<Book> deserializeBooks(List<T> genericBooks) {
//
//        if(genericBooks.get(0) instanceof String) {
//            List<String> intBooks = (List<String>) genericBooks;
//            List<Book> bookList = intBooks.stream().map(Book::of).toList();
////            bookList.forEach(System.out::println);
//            System.out.println(bookList.toString());
//            return bookList;
//        }
//
//        else if(genericBooks.get(0) instanceof Map) {
//            List<Map> bookMap = (List<Map>) genericBooks;
//            List<Book> bookList = new ArrayList<>();
//            bookMap.forEach((book) -> {
//                Book tempBook = new Book();
//
//                if(book.containsKey("isbn") && book.get("isbn") instanceof String)
//                    tempBook.setIsbn((String) book.get("isbn"));
//                else throw new InvalidArgumentException("Book ISBN is not valid!");
//
//                if(book.containsKey("title") && book.get("title") instanceof String)
//                    tempBook.setTitle((String) book.get("title"));
//                else throw new InvalidArgumentException("Book title is not valid!");
//
//                if(book.containsKey("datePurchased") && book.get("datePurchased") instanceof String)
//                    tempBook.setDatePurchased(LocalDate.parse((String) book.get("datePurchased")));
//                else throw new InvalidArgumentException("Book date purchased is not valid!");
//
//                if(book.containsKey("availableCopies") && book.get("availableCopies") instanceof Integer)
//                    tempBook.setAvailableCopies((Integer) book.get("availableCopies"));
//                else throw new InvalidArgumentException("Book available copies is not valid!");
//
//                if(book.containsKey("totalCopies") && book.get("totalCopies") instanceof Integer)
//                    tempBook.setTotalCopies((Integer) book.get("totalCopies"));
//                else throw new InvalidArgumentException("Book total copies is not valid!");
//
//                bookList.add(tempBook);
//            });
//
//            System.out.println(bookList.toString());
//            return bookList;
//        }
//
//        else throw new InvalidArgumentException("Author 'books' is not valid!");
//    }

    @JsonProperty("books")
    public List<String> getBookIsbns() {
        this.fillInBookIsbns();
        return this.bookIsbns;
    }

    @Override
    public Author clone() throws CloneNotSupportedException {
        return (Author) super.clone();
    }
}
