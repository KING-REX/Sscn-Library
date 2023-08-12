package com.sscn.library.controller;

import com.sscn.library.entity.Book;
import com.sscn.library.entity.BookIssuance;
import com.sscn.library.entity.Member;
import com.sscn.library.service.BookIssuanceService;
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
    public List<BookIssuance> getAllBookIssuances() {
        return bookIssuanceService.getAllBookIssuances();
    }

    @GetMapping("/{id}")
    public BookIssuance getBookIssuanceById(int id) {
        return bookIssuanceService.getBookIssuanceById(id);
    }

    @GetMapping("/{bookIsbn}")
    public List<BookIssuance> getBookIssuancesByBook(Book book) {
        return bookIssuanceService.getBookIssuancesByBook(book);
    }

    @GetMapping("/{dateIssued}.di")
    public List<BookIssuance> getBookIssuancesByDateIssued(LocalDate dateIssued) {
        return bookIssuanceService.getBookIssuancesByDateIssued(dateIssued);
    }

    @GetMapping("/{dateDue}.dd")
    public List<BookIssuance> getBookIssuancesByDateDue(LocalDate dateDue) {
        return bookIssuanceService.getBookIssuancesByDateDue(dateDue);
    }

    @GetMapping("/{id}.mi")
    public List<BookIssuance> getBookIssuancesByMemberId(Integer id) {
        return bookIssuanceService.getBookIssuancesByMemberId(id);
    }


    @PostMapping("/member")
    public List<BookIssuance> getBookIssuancesByMember(@RequestBody Member issuedTo) {
        return bookIssuanceService.getBookIssuancesByMemberIssuedTo(issuedTo);
    }

    @PostMapping
    public List<BookIssuance> addBookIssuances(List<BookIssuance> bookIssuances) {
        return bookIssuanceService.addBookIssuances(bookIssuances);
    }

    @PutMapping("/{id}")
    public BookIssuance updateBookIssuance(BookIssuance newBookIssuance, Integer id) {
        return bookIssuanceService.updateBookIssuance(newBookIssuance, id);
    }

    @DeleteMapping
    public void deleteAllBookIssuances() {
        bookIssuanceService.deleteAllBookIssuances();
    }

    @DeleteMapping("/{id}")
    public void deleteBookIssuanceById(Integer id) {
        bookIssuanceService.deleteBookIssuanceById(id);
    }

    @DeleteMapping("/{bookIsbn}")
    public void deleteBookIssuancesByBookIsbn(String bookIsbn) {
        bookIssuanceService.deleteBookIssuancesByBookIsbn(bookIsbn);
    }

    @DeleteMapping("/{dateIssued}.di")
    public void deleteBookIssuancesByDateIssued(LocalDate dateIssued) {
        bookIssuanceService.deleteBookIssuancesByDateIssued(dateIssued);
    }

    @DeleteMapping("/{dateDue}.dd")
    public void deleteBookIssuancesByDateDue(LocalDate dateDue) {
        bookIssuanceService.deleteBookIssuancesByDateDue(dateDue);
    }

    @DeleteMapping("/{id}.mi")
    public void deleteBookIssuancesByMemberId(Integer id) {
        bookIssuanceService.deleteBookIssuancesByMemberId(id);
    }
}
