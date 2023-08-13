package com.sscn.library.controller;

import com.sscn.library.entity.Book;
import com.sscn.library.service.BookService;
import org.springframework.cglib.core.Local;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/v1/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{isbn}")
    public Book getBookByIsbn(@PathVariable String isbn) {
        return bookService.getBookByIsbn(isbn);
    }

    @GetMapping("/{title}.t")
    public List<Book> getBooksByTitle(@PathVariable String title) {
        return bookService.getBooksByTitle(title);
    }

    @GetMapping("/{datePurchased}.d")
    public List<Book> getBookByDatePurchased(@PathVariable LocalDate datePurchased) {
        return bookService.getBooksByDatePurchased(datePurchased);
    }

    @PostMapping
    public List<Book> addBooks(@RequestBody List<Book> books) {
        return bookService.addBooks(books);
    }

    @PutMapping("/{bookIsbn}")
    public Book updateBook(@RequestBody Book newBook, @PathVariable String bookIsbn) {
        return bookService.updateBook(newBook, bookIsbn);
    }

    @DeleteMapping
    public void deleteAllBooks() {
        bookService.deleteAllBooks();
    }

    @DeleteMapping("/{isbn}")
    public void deleteBookByIsbn(@PathVariable String isbn) {
        bookService.deleteBookByIsbn(isbn);
    }

    @DeleteMapping("/{title}.t")
    public void deleteBooksByTitle(@PathVariable String title) {
        bookService.deleteBooksByTitle(title);
    }

    @DeleteMapping("/{datePurchased}.d")
    public void deleteBooksByDatePurchased(@PathVariable LocalDate datePurchased) {
        bookService.deleteBooksByDatePurchased(datePurchased);
    }
}
