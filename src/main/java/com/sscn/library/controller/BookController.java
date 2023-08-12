package com.sscn.library.controller;

import com.sscn.library.entity.Book;
import com.sscn.library.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/{isbn}")
    public Book getBookByIsbn(@PathVariable String isbn) {
        return bookService.getBookByIsbn(isbn);
    }

    @GetMapping
    public List<Book> getBooks() {
        return bookService.getAllBooks();
    }

    @PostMapping
    public Book addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    @PutMapping
    public Book updateBook(@RequestBody Book newBook, String bookIsbn) {
        return bookService.updateBook(newBook, bookIsbn);
    }

    @DeleteMapping
    public void deleteBookByIsbn(@RequestBody String isbn) {
        bookService.deleteBookByIsbn(isbn);
    }
}
