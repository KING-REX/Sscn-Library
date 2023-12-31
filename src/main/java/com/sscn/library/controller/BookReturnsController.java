package com.sscn.library.controller;

import com.sscn.library.entity.Book;
import com.sscn.library.entity.BookIssuance;
import com.sscn.library.entity.BookReturns;
import com.sscn.library.entity.ReturnStatus;
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
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookReturns> getBookReturnById(@PathVariable Integer id) throws NotFoundException {
        return ResponseEntity.ok(bookReturnsService.getBookReturnById(id));
    }

//    public List<BookReturns> getBookReturnsByBookIssued(Book bookIssued) {
//        return bookReturnsRepository.findAllByBookIssued(bookIssued).orElseThrow(() -> new NotFoundException("Book Returns for book %s does not exist.".formatted(bookIssued.getIsbn())));
//    }

    @GetMapping("/{bookIsbn}.i")
    public ResponseEntity<List<BookReturns>> getBookReturnsByBookIsbn(@PathVariable String bookIsbn) throws NotFoundException {
        return ResponseEntity.ok(bookReturnsService.getBookReturnsByBookIsbn(bookIsbn));
    }

    @GetMapping("/{bookIssuanceId}.b")
    public ResponseEntity<List<BookReturns>> getBookReturnsByBookIssuanceId(@PathVariable Integer bookIssuanceId) throws NotFoundException {
        return ResponseEntity.ok(bookReturnsService.getBookReturnsByBookIssuanceId(bookIssuanceId));

    }

    @GetMapping("/{returnStatus}.rs")
    public ResponseEntity<List<BookReturns>> getBookReturnsByReturnStatus(@PathVariable ReturnStatus returnStatus) throws NotFoundException {
        return ResponseEntity.ok(bookReturnsService.getBookReturnsByReturnStatus(returnStatus));

    }

    @GetMapping("/{dateReturned}.d")
    public ResponseEntity<List<BookReturns>> getBookReturnsByDateReturned(@PathVariable LocalDate dateReturned) throws NotFoundException {
        return ResponseEntity.ok(bookReturnsService.getBookReturnsByDateReturned(dateReturned));
    }

    @PostMapping
    public ResponseEntity<List<BookReturns>> addBookReturns(@RequestBody List<BookReturns> bookReturns) throws DuplicateValueException, IllegalStateException, IllegalArgumentException {
        return ResponseEntity.ok(bookReturnsService.addBookReturns(bookReturns));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookReturns> updateBookReturns(@RequestBody BookReturns newBookReturns, @PathVariable Integer id) throws NotFoundException, IllegalArgumentException {
        return ResponseEntity.ok(bookReturnsService.updateBookReturns(newBookReturns, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity.BodyBuilder deleteBookReturnById(@PathVariable Integer id) throws IllegalArgumentException, NotFoundException {
        bookReturnsService.deleteBookReturnById(id);
        return ResponseEntity.ok();
    }

    @DeleteMapping("/{bookIsbn}.i")
    public ResponseEntity.BodyBuilder deleteBookReturnsByBookIsbn(@PathVariable String isbn) throws IllegalArgumentException, NotFoundException {
        bookReturnsService.deleteBookReturnsByBookIsbn(isbn);
        return ResponseEntity.ok();
    }

    @DeleteMapping("/{bookIssuanceId}.b")
    public ResponseEntity.BodyBuilder deleteBookReturnsByBookIssuanceId(@PathVariable Integer id) throws IllegalArgumentException, NotFoundException {
        bookReturnsService.deleteBookReturnsByBookIssuanceId(id);
        return ResponseEntity.ok();
    }

    @DeleteMapping("/{dateReturned}.d")
    public ResponseEntity.BodyBuilder deleteBookReturnsByDateReturned(@PathVariable LocalDate dateReturned) throws IllegalArgumentException, NotFoundException {
        bookReturnsService.deleteBookReturnsByDateReturned(dateReturned);
        return ResponseEntity.ok();
    }

    @DeleteMapping("/{returnStatus}.rs")
    public ResponseEntity.BodyBuilder deleteBookReturnsByReturnStatus(@PathVariable ReturnStatus returnStatus) throws IllegalArgumentException, NotFoundException {
        bookReturnsService.deleteBookReturnsByReturnStatus(returnStatus);
        return ResponseEntity.ok();
    }

    @DeleteMapping
    public ResponseEntity.BodyBuilder deleteAllBookReturns() {
        bookReturnsService.deleteAllBookReturns();return ResponseEntity.ok();
    }
}
