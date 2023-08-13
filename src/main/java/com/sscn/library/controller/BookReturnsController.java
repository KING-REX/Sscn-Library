package com.sscn.library.controller;

import com.sscn.library.entity.Book;
import com.sscn.library.entity.BookIssuance;
import com.sscn.library.entity.BookReturns;
import com.sscn.library.exception.DuplicateValueException;
import com.sscn.library.exception.NotFoundException;
import com.sscn.library.service.BookReturnsService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<BookReturns>> getAllBookReturns() {
        return ResponseEntity.ok(bookReturnsService.getAllBookReturns());
    };

    @GetMapping("/{id}")
    public ResponseEntity<BookReturns>  getBookReturnById(@PathVariable Integer id) {
        return ResponseEntity.ok(bookReturnsService.getBookReturnById(id));
    }

//    public List<BookReturns> getBookReturnsByBookIssued(Book bookIssued) {
//        return bookReturnsRepository.findAllByBookIssued(bookIssued).orElseThrow(() -> new NotFoundException("Book Returns for book %s does not exist.".formatted(bookIssued.getIsbn())));
//    }

    @GetMapping("/{bookIsbn}.i")
    public ResponseEntity<List<BookReturns>>  getBookReturnsByBookIsbn(@PathVariable String bookIsbn) {
        return ResponseEntity.ok(bookReturnsService.getBookReturnsByBookIsbn(bookIsbn));
    }

    @GetMapping("/{bookIssuanceId}.b")
    public ResponseEntity<List<BookReturns>>  getBookReturnsByBookIssuanceId(@PathVariable Integer bookIssuanceId) {
        return ResponseEntity.ok(bookReturnsService.getBookReturnsByBookIssuanceId(bookIssuanceId));

    }

    @GetMapping("/{dateReturned}.d")
    public ResponseEntity<List<BookReturns>>  getBookReturnsByDateReturned(@PathVariable LocalDate dateReturned) {
        return ResponseEntity.ok(bookReturnsService.getBookReturnsByDateReturned(dateReturned));
    }

    @PostMapping
    public ResponseEntity<List<BookReturns>>  addBookReturns(@RequestBody List<BookReturns> bookReturns) {
        return ResponseEntity.ok(bookReturnsService.addBookReturns(bookReturns));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookReturns>  updateBookReturns(@RequestBody BookReturns newBookReturns, @PathVariable Integer id) {
        return ResponseEntity.ok(bookReturnsService.updateBookReturns(newBookReturns, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity.BodyBuilder deleteBookReturnById(Integer id) {
        bookReturnsService.deleteBookReturnById(id);
        return ResponseEntity.ok();
    }

    @DeleteMapping("/{bookIsbn}.i")
    public ResponseEntity.BodyBuilder deleteBookReturnsByBookIsbn(String isbn) {
        bookReturnsService.deleteBookReturnsByBookIsbn(isbn);
        return ResponseEntity.ok();
    };

    @DeleteMapping("/{bookIssuanceId}.b")
    public ResponseEntity.BodyBuilder deleteBookReturnsByBookIssuanceId(Integer id) {
        bookReturnsService.deleteBookReturnsByBookIssuanceId(id);
        return ResponseEntity.ok();
    };

    @DeleteMapping("/{dateReturned}.d")
    public ResponseEntity.BodyBuilder deleteBookReturnsByDateReturned(LocalDate dateReturned) {
        bookReturnsService.deleteBookReturnsByDateReturned(dateReturned);
        return ResponseEntity.ok();
    };

    @DeleteMapping
    public ResponseEntity.BodyBuilder deleteAllBookReturns() {
        bookReturnsService.deleteAllBookReturns();return ResponseEntity.ok();
    }
}
