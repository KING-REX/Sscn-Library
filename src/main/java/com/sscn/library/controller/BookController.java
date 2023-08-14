package com.sscn.library.controller;

import com.sscn.library.entity.Book;
import com.sscn.library.exception.DuplicateValueException;
import com.sscn.library.exception.NotFoundException;
import com.sscn.library.service.BookService;
import org.apache.coyote.Response;
import org.springframework.cglib.core.Local;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Book>> getBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<Book> getBookByIsbn(@PathVariable String isbn) throws NotFoundException {
        return ResponseEntity.ok(bookService.getBookByIsbn(isbn));
    }

    @GetMapping("/{title}.t")
    public ResponseEntity<List<Book>> getBooksByTitle(@PathVariable String title) throws NotFoundException {
        return ResponseEntity.ok(bookService.getBooksByTitle(title));
    }

    @GetMapping("/{datePurchased}.d")
    public ResponseEntity<List<Book>>  getBookByDatePurchased(@PathVariable LocalDate datePurchased) throws NotFoundException {
        return ResponseEntity.ok(bookService.getBooksByDatePurchased(datePurchased));
    }

    @PostMapping
    public ResponseEntity<List<Book>>  addBooks(@RequestBody List<Book> books) throws DuplicateValueException, IllegalStateException, IllegalArgumentException {
        return ResponseEntity.ok(bookService.addBooks(books));
    }

    @PutMapping("/{bookIsbn}")
    public ResponseEntity<Book>  updateBook(@RequestBody Book newBook, @PathVariable String bookIsbn) throws NotFoundException, IllegalArgumentException {
        return ResponseEntity.ok(bookService.updateBook(newBook, bookIsbn));
    }

    @DeleteMapping
    public ResponseEntity.BodyBuilder deleteAllBooks() {
        bookService.deleteAllBooks();
        return ResponseEntity.ok();
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity.BodyBuilder deleteBookByIsbn(@PathVariable String isbn) throws IllegalArgumentException, NotFoundException {
        bookService.deleteBookByIsbn(isbn);
        return ResponseEntity.ok();
    }

    @DeleteMapping("/{title}.t")
    public ResponseEntity.BodyBuilder deleteBooksByTitle(@PathVariable String title) throws IllegalArgumentException, NotFoundException {
        bookService.deleteBooksByTitle(title);
        return ResponseEntity.ok();
    }

    @DeleteMapping("/{datePurchased}.d")
    public ResponseEntity.BodyBuilder deleteBooksByDatePurchased(@PathVariable LocalDate datePurchased) throws IllegalArgumentException, NotFoundException {
        bookService.deleteBooksByDatePurchased(datePurchased);
        return ResponseEntity.ok();
    }
}
