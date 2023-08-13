package com.sscn.library.controller;

import com.sscn.library.entity.Book;
import com.sscn.library.entity.BookIssuance;
import com.sscn.library.entity.Member;
import com.sscn.library.service.BookIssuanceService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/v1/bookIssuance")
public class BookIssuanceController {

    private final BookIssuanceService bookIssuanceService;

    public BookIssuanceController(BookIssuanceService bookIssuanceService) {
        this.bookIssuanceService = bookIssuanceService;
    }

    @GetMapping
    public ResponseEntity<List<BookIssuance>> getAllBookIssuances() {
        return ResponseEntity.ok(bookIssuanceService.getAllBookIssuances());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookIssuance>  getBookIssuanceById(int id) {
        return ResponseEntity.ok(bookIssuanceService.getBookIssuanceById(id));
    }

    @GetMapping("/{bookIsbn}.i")
    public ResponseEntity< List<BookIssuance>> getBookIssuancesByBook(Book book) {
        return ResponseEntity.ok(bookIssuanceService.getBookIssuancesByBook(book));
    }

    @GetMapping("/{dateIssued}.di")
    public ResponseEntity<List<BookIssuance>>  getBookIssuancesByDateIssued(LocalDate dateIssued) {
        return ResponseEntity.ok( bookIssuanceService.getBookIssuancesByDateIssued(dateIssued));
    }

    @GetMapping("/{dateDue}.dd")
    public ResponseEntity<List<BookIssuance>>  getBookIssuancesByDateDue(LocalDate dateDue) {
        return ResponseEntity.ok(bookIssuanceService.getBookIssuancesByDateDue(dateDue));
    }

    @GetMapping("/{memberId}.mi")
    public ResponseEntity<List<BookIssuance>>  getBookIssuancesByMemberId(Integer memberId) {
        return ResponseEntity.ok(bookIssuanceService.getBookIssuancesByMemberId(memberId));
    }


//    @PostMapping("/member")
//    public List<BookIssuance> getBookIssuancesByMember(@RequestBody Member issuedTo) {
//        return bookIssuanceService.getBookIssuancesByMemberIssuedTo(issuedTo);
//    }

    @PostMapping
    public ResponseEntity<List<BookIssuance>> addBookIssuances(List<BookIssuance> bookIssuances) {
        return ResponseEntity.ok(bookIssuanceService.addBookIssuances(bookIssuances));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookIssuance>  updateBookIssuance(BookIssuance newBookIssuance, Integer id) {
        return ResponseEntity.ok(bookIssuanceService.updateBookIssuance(newBookIssuance, id));
    }

    @DeleteMapping
    public ResponseEntity.BodyBuilder deleteAllBookIssuances() {
        bookIssuanceService.deleteAllBookIssuances();
        return ResponseEntity.ok();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity.BodyBuilder deleteBookIssuanceById(Integer id) {
        bookIssuanceService.deleteBookIssuanceById(id);
        return ResponseEntity.ok();
    }

    @DeleteMapping("/{bookIsbn}.i")
    public ResponseEntity.BodyBuilder deleteBookIssuancesByBookIsbn(String bookIsbn) {
        bookIssuanceService.deleteBookIssuancesByBookIsbn(bookIsbn);
        return ResponseEntity.ok();
    }

    @DeleteMapping("/{dateIssued}.di")
    public ResponseEntity.BodyBuilder deleteBookIssuancesByDateIssued(LocalDate dateIssued) {
        bookIssuanceService.deleteBookIssuancesByDateIssued(dateIssued);
        return ResponseEntity.ok();
    }

    @DeleteMapping("/{dateDue}.dd")
    public ResponseEntity.BodyBuilder deleteBookIssuancesByDateDue(LocalDate dateDue) {
        bookIssuanceService.deleteBookIssuancesByDateDue(dateDue);
        return ResponseEntity.ok();
    }

    @DeleteMapping("/{memberId}.mi")
    public ResponseEntity.BodyBuilder deleteBookIssuancesByMemberId(Integer memberId) {
        bookIssuanceService.deleteBookIssuancesByMemberId(memberId);
        return ResponseEntity.ok();
    }
}
