package com.sscn.library.service;

import com.sscn.library.entity.Book;
import com.sscn.library.entity.BookIssuance;
import com.sscn.library.entity.BookReturns;
import com.sscn.library.exception.DuplicateValueException;
import com.sscn.library.exception.NotFoundException;
import com.sscn.library.repository.BookReturnsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookReturnsService {
    private final BookReturnsRepository bookReturnsRepository;
    private final BookService bookService;
    private final BookIssuanceService bookIssuanceService;

    public BookReturnsService(BookReturnsRepository bookReturnsRepository, BookService bookService, BookIssuanceService bookIssuanceService) {
        this.bookReturnsRepository = bookReturnsRepository;
        this.bookService = bookService;
        this.bookIssuanceService = bookIssuanceService;
    }

    public List<BookReturns> getAllBookReturns() {
        return bookReturnsRepository.findAll();
    };

    public BookReturns getBookReturnById(Integer id) {
        return bookReturnsRepository.findById(id).orElseThrow(() -> new NotFoundException("Book Returns with id %s does not exist.".formatted(id)));
    }

    public List<BookReturns> getBookReturnsByBookIssued(Book bookIssued) {
        return bookReturnsRepository.findAllByBookIssued(bookIssued).orElseThrow(() -> new NotFoundException("Book Returns for book %s does not exist.".formatted(bookIssued.getIsbn())));
    }

    public List<BookReturns> getBookReturnsByBookIsbn(String isbn) {
        return getBookReturnsByBookIssued(bookService.getBookByIsbn(isbn));
    }

    public List<BookReturns> getBookReturnsByBookIssuance(BookIssuance bookIssuance) {
        return bookReturnsRepository.findAllByBookIssuance(bookIssuance).orElseThrow(() -> new NotFoundException("Book Returns for book issuance %s does not exist.".formatted(bookIssuance.getId())));
    }

    public List<BookReturns> getBookReturnsByBookIssuanceId(Integer id) {
        return getBookReturnsByBookIssuance(bookIssuanceService.getBookIssuanceById(id));
    }

    public List<BookReturns> getBookReturnsByDateReturned(LocalDate dateReturned) {
        return bookReturnsRepository.findAllByDateReturned(dateReturned).orElseThrow(() -> new NotFoundException("Book Returns on %s does not exist.".formatted(dateReturned)));
    }

    public BookReturns addBookReturns(BookReturns bookReturns) {
        if(bookReturns.getId() != null && bookReturnsRepository.existsById(bookReturns.getId()))
            throw new DuplicateValueException("Book Returns %s already exists".formatted(bookReturns.getId()));

        return bookReturnsRepository.save(bookReturns);
    }

    public List<BookReturns> addBookReturns(List<BookReturns> bookReturns) {
        bookReturns.forEach(this::addBookReturns);

        return bookReturns;
    }

    public BookReturns updateBookReturns(BookReturns newBookReturns, Integer id) {
        BookReturns oldBookReturns = getBookReturnById(id);

        if(newBookReturns.getBookIssued() != null && newBookReturns.getBookIssued().getIsbn() != null)
            oldBookReturns.setBookIssued(newBookReturns.getBookIssued());
        if(newBookReturns.getBookIssuance() != null && newBookReturns.getBookIssuance().getId() != null)
            oldBookReturns.setBookIssuance(newBookReturns.getBookIssuance());
        if(newBookReturns.getDateReturned() != null && (newBookReturns.getDateReturned().isBefore(LocalDate.now()) || newBookReturns.getDateReturned().isEqual(LocalDate.now())))
            oldBookReturns.setDateReturned(newBookReturns.getDateReturned());

        return bookReturnsRepository.save(oldBookReturns);
    }

    public void deleteBookReturns(BookReturns bookReturns) {
        bookReturnsRepository.delete(bookReturns);
    }

    public void deleteBookReturnById(Integer id) {
        bookReturnsRepository.delete(getBookReturnById(id));
    }

    public void deleteBookReturnsByBookIssued(Book bookIssued) {
        getBookReturnsByBookIssued(bookIssued).forEach(this::deleteBookReturns);
    };

    public void deleteBookReturnsByBookIsbn(String isbn) {
        getBookReturnsByBookIsbn(isbn).forEach(this::deleteBookReturns);
    };

    public void deleteBookReturnsByBookIssuance(BookIssuance bookIssuance) {
        getBookReturnsByBookIssuance(bookIssuance).forEach(this::deleteBookReturns);
    };

    public void deleteBookReturnsByBookIssuanceId(Integer id) {
        getBookReturnsByBookIssuanceId(id).forEach(this::deleteBookReturns);
    };

    public void deleteBookReturnsByDateReturned(LocalDate dateReturned) {
        getBookReturnsByDateReturned(dateReturned).forEach(this::deleteBookReturns);
    };

    public void deleteAllBookReturns() {
        bookReturnsRepository.deleteAll();
    }
}