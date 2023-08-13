package com.sscn.library.controller;

import com.sscn.library.entity.Book;
import com.sscn.library.entity.BookIssuance;
import com.sscn.library.entity.BookReturns;
import com.sscn.library.exception.DuplicateValueException;
import com.sscn.library.exception.NotFoundException;
import com.sscn.library.service.BookReturnsService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/v1/bookReturns")
public class BookReturnsController {
    private final BookReturnsService bookReturnsService;

    public BookReturnsController(BookReturnsService bookReturnsService) {
        this.bookReturnsService = bookReturnsService;
    }

    @GetMapping
    public List<BookReturns> getAllBookReturns() {
        return bookReturnsService.getAllBookReturns();
    };

    @GetMapping("/{id}")
    public BookReturns getBookReturnById(@PathVariable Integer id) {
        return bookReturnsService.getBookReturnById(id);
    }

//    public List<BookReturns> getBookReturnsByBookIssued(Book bookIssued) {
//        return bookReturnsRepository.findAllByBookIssued(bookIssued).orElseThrow(() -> new NotFoundException("Book Returns for book %s does not exist.".formatted(bookIssued.getIsbn())));
//    }

    @GetMapping("/{bookIsbn}.i")
    public List<BookReturns> getBookReturnsByBookIsbn(@PathVariable String bookIsbn) {
        return bookReturnsService.getBookReturnsByBookIsbn(bookIsbn);
    }

    @GetMapping("/{bookIssuanceId}.b")
    public List<BookReturns> getBookReturnsByBookIssuanceId(@PathVariable Integer bookIssuanceId) {
        return bookReturnsService.getBookReturnsByBookIssuanceId(bookIssuanceId);
    }

    @GetMapping("/{dateReturned}")
    public List<BookReturns> getBookReturnsByDateReturned(@PathVariable LocalDate dateReturned) {
        return bookReturnsService.getBookReturnsByDateReturned(dateReturned);
    }

    @PostMapping
    public List<BookReturns> addBookReturns(@RequestBody List<BookReturns> bookReturns) {
        return bookReturnsService.addBookReturns(bookReturns);
    }

    @PutMapping("/{id}")
    public BookReturns updateBookReturns(@RequestBody BookReturns newBookReturns, @PathVariable Integer id) {
        return bookReturnsService.updateBookReturns(newBookReturns, id);
    }

    @DeleteMapping("/{id}")
    public void deleteBookReturnById(Integer id) {
        bookReturnsService.deleteBookReturnById(id);
    }

    @DeleteMapping("/{bookIsbn}.i")
    public void deleteBookReturnsByBookIsbn(String isbn) {
        bookReturnsService.deleteBookReturnsByBookIsbn(isbn);
    };

    @DeleteMapping("/{bookIssuanceId}.b")
    public void deleteBookReturnsByBookIssuanceId(Integer id) {
        bookReturnsService.deleteBookReturnsByBookIssuanceId(id);
    };

    @DeleteMapping("/{dateReturned}")
    public void deleteBookReturnsByDateReturned(LocalDate dateReturned) {
        bookReturnsService.deleteBookReturnsByDateReturned(dateReturned);
    };

    @DeleteMapping
    public void deleteAllBookReturns() {
        bookReturnsService.deleteAllBookReturns();
    }
}
