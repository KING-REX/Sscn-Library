package com.sscn.library.controller;

import com.sscn.library.entity.BookIssuance;
import com.sscn.library.entity.BorrowStatus;
import com.sscn.library.exception.DuplicateValueException;
import com.sscn.library.exception.NotFoundException;
import com.sscn.library.service.BookIssuanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/v1/bookIssuances")
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

    @GetMapping("/{copiesIssued}.ci")
    public ResponseEntity<List<BookIssuance>>  getBookIssuancesByCopiesIssued(@PathVariable Integer copiesIssued) throws NotFoundException {
        return ResponseEntity.ok(bookIssuanceService.getBookIssuancesByCopiesIssued(copiesIssued));
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
    public ResponseEntity<BookIssuance>  updateBookIssuance(@RequestBody BookIssuance newBookIssuance, @PathVariable Integer id) throws NotFoundException, IllegalArgumentException, CloneNotSupportedException {
        return ResponseEntity.ok(bookIssuanceService.updateBookIssuance(newBookIssuance, id));
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteAllBookIssuances() throws UnsupportedOperationException {
        return ResponseEntity.ok(bookIssuanceService.deleteAllBookIssuances());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteBookIssuanceById(@PathVariable Integer id) throws UnsupportedOperationException, IllegalArgumentException, NotFoundException {
        return ResponseEntity.ok(bookIssuanceService.deleteBookIssuanceById(id));
    }

    @DeleteMapping("/{bookIsbn}.i")
    public ResponseEntity<Boolean> deleteBookIssuancesByBookIsbn(@PathVariable String bookIsbn) throws UnsupportedOperationException, IllegalArgumentException, NotFoundException {
        return ResponseEntity.ok(bookIssuanceService.deleteBookIssuancesByBookIsbn(bookIsbn));
    }

    @DeleteMapping("/{dateIssued}.di")
    public ResponseEntity<Boolean> deleteBookIssuancesByDateIssued(@PathVariable LocalDate dateIssued) throws UnsupportedOperationException, IllegalArgumentException, NotFoundException {
        return ResponseEntity.ok(bookIssuanceService.deleteBookIssuancesByDateIssued(dateIssued));
    }

    @DeleteMapping("/{dateDue}.dd")
    public ResponseEntity<Boolean> deleteBookIssuancesByDateDue(@PathVariable LocalDate dateDue) throws UnsupportedOperationException, IllegalArgumentException, NotFoundException {
        return ResponseEntity.ok(bookIssuanceService.deleteBookIssuancesByDateDue(dateDue));
    }

    @DeleteMapping("/{memberId}.mi")
    public ResponseEntity<Boolean> deleteBookIssuancesByMemberId(@PathVariable Integer memberId) throws UnsupportedOperationException, IllegalArgumentException, NotFoundException {
        return ResponseEntity.ok(bookIssuanceService.deleteBookIssuancesByMemberId(memberId));
    }

    @DeleteMapping("/{copiesIssued}.ci")
    public ResponseEntity<Boolean> deleteBookIssuancesByCopiesIssued(@PathVariable Integer copiesIssued) throws UnsupportedOperationException, IllegalArgumentException, NotFoundException {
        return ResponseEntity.ok(bookIssuanceService.deleteBookIssuancesByCopiesIssued(copiesIssued));
    }

    @DeleteMapping("/{borrowStatus}.rs")
    public ResponseEntity<Boolean> deleteBookIssuancesByBorrowStatus(@PathVariable BorrowStatus borrowStatus) throws UnsupportedOperationException, IllegalArgumentException, NotFoundException {
        return ResponseEntity.ok(bookIssuanceService.deleteBookIssuancesByBorrowStatus(borrowStatus));
    }
}
