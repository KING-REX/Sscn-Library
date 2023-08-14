package com.sscn.library.controller;

import com.sscn.library.entity.Book;
import com.sscn.library.entity.BookIssuance;
import com.sscn.library.entity.Member;
import com.sscn.library.entity.ReturnStatus;
import com.sscn.library.exception.DuplicateValueException;
import com.sscn.library.exception.NotFoundException;
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
    public ResponseEntity<BookIssuance> getBookIssuanceById(@PathVariable Integer id) throws NotFoundException {
        return ResponseEntity.ok(bookIssuanceService.getBookIssuanceById(id));
    }

    @GetMapping("/{bookIsbn}.i")
    public ResponseEntity< List<BookIssuance>> getBookIssuancesByBookIsbn(@PathVariable String bookIsbn) throws NotFoundException {
        System.out.println(bookIsbn);
        return ResponseEntity.ok(bookIssuanceService.getBookIssuancesByBookIsbn(bookIsbn));
    }

    @GetMapping("/{dateIssued}.di")
    public ResponseEntity<List<BookIssuance>>  getBookIssuancesByDateIssued(@PathVariable LocalDate dateIssued) throws NotFoundException {
        return ResponseEntity.ok( bookIssuanceService.getBookIssuancesByDateIssued(dateIssued));
    }

    @GetMapping("/{dateDue}.dd")
    public ResponseEntity<List<BookIssuance>>  getBookIssuancesByDateDue(@PathVariable LocalDate dateDue) throws NotFoundException {
        return ResponseEntity.ok(bookIssuanceService.getBookIssuancesByDateDue(dateDue));
    }

    @GetMapping("/{memberId}.mi")
    public ResponseEntity<List<BookIssuance>>  getBookIssuancesByMemberId(@PathVariable Integer memberId) throws NotFoundException {
        return ResponseEntity.ok(bookIssuanceService.getBookIssuancesByMemberId(memberId));
    }


//    @PostMapping("/member")
//    public List<BookIssuance> getBookIssuancesByMember(@RequestBody Member issuedTo) {
//        return bookIssuanceService.getBookIssuancesByMemberIssuedTo(issuedTo);
//    }

    @PostMapping
    public ResponseEntity<List<BookIssuance>> addBookIssuances(@RequestBody List<BookIssuance> bookIssuances) throws DuplicateValueException, IllegalStateException, IllegalArgumentException {
        return ResponseEntity.ok(bookIssuanceService.addBookIssuances(bookIssuances));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookIssuance>  updateBookIssuance(@RequestBody BookIssuance newBookIssuance, @PathVariable Integer id) throws NotFoundException, IllegalArgumentException {
        return ResponseEntity.ok(bookIssuanceService.updateBookIssuance(newBookIssuance, id));
    }

    @DeleteMapping
    public ResponseEntity.BodyBuilder deleteAllBookIssuances() {
        bookIssuanceService.deleteAllBookIssuances();
        return ResponseEntity.ok();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity.BodyBuilder deleteBookIssuanceById(@PathVariable Integer id) throws IllegalArgumentException, NotFoundException {
        bookIssuanceService.deleteBookIssuanceById(id);
        return ResponseEntity.ok();
    }

    @DeleteMapping("/{bookIsbn}.i")
    public ResponseEntity.BodyBuilder deleteBookIssuancesByBookIsbn(@PathVariable String bookIsbn) throws IllegalArgumentException, NotFoundException {
        bookIssuanceService.deleteBookIssuancesByBookIsbn(bookIsbn);
        return ResponseEntity.ok();
    }

    @DeleteMapping("/{dateIssued}.di")
    public ResponseEntity.BodyBuilder deleteBookIssuancesByDateIssued(@PathVariable LocalDate dateIssued) throws IllegalArgumentException, NotFoundException {
        bookIssuanceService.deleteBookIssuancesByDateIssued(dateIssued);
        return ResponseEntity.ok();
    }

    @DeleteMapping("/{dateDue}.dd")
    public ResponseEntity.BodyBuilder deleteBookIssuancesByDateDue(@PathVariable LocalDate dateDue) throws IllegalArgumentException, NotFoundException {
        bookIssuanceService.deleteBookIssuancesByDateDue(dateDue);
        return ResponseEntity.ok();
    }

    @DeleteMapping("/{memberId}.mi")
    public ResponseEntity.BodyBuilder deleteBookIssuancesByMemberId(@PathVariable Integer memberId) throws IllegalArgumentException, NotFoundException {
        bookIssuanceService.deleteBookIssuancesByMemberId(memberId);
        return ResponseEntity.ok();
    }

    @DeleteMapping("/{returnStatus}.rs")
    public ResponseEntity.BodyBuilder deleteBookIssuancesByReturnStatus(@PathVariable ReturnStatus returnStatus) throws IllegalArgumentException, NotFoundException {
        bookIssuanceService.deleteBookIssuancesByReturnStatus(returnStatus);
        return ResponseEntity.ok();
    }
}
