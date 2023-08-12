package com.sscn.library.controller;

import com.sscn.library.entity.Book;
import com.sscn.library.entity.BookIssuance;
import com.sscn.library.service.BookIssuanceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping
    public BookIssuance addBookIssuance(BookIssuance bookIssuance) {
        return bookIssuanceService.addBookIssuance(bookIssuance);
    }


}
