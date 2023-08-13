package com.sscn.library.service;

import com.sscn.library.entity.Book;
import com.sscn.library.entity.BookIssuance;
import com.sscn.library.entity.Member;
import com.sscn.library.entity.ReturnStatus;
import com.sscn.library.exception.DuplicateValueException;
import com.sscn.library.exception.NotFoundException;
import com.sscn.library.repository.BookIssuanceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookIssuanceService {

    private final BookIssuanceRepository bookIssuanceRepository;
    private final BookService bookService;

    private final MemberService memberService;

    public BookIssuanceService(BookIssuanceRepository bookIssuanceRepository, BookService bookService, MemberService memberService) {
        this.bookIssuanceRepository = bookIssuanceRepository;
        this.bookService = bookService;
        this.memberService = memberService;
    }

    public List<BookIssuance> getAllBookIssuances() {
        return bookIssuanceRepository.findAll();
    }

    public BookIssuance getBookIssuanceById(int id) {
        return bookIssuanceRepository.findById(id).orElseThrow(() -> new NotFoundException("Book Issuance Record with id %s not found".formatted(id)));
    }

    public List<BookIssuance> getBookIssuancesByBookIsbn(String isbn) {
        return getBookIssuancesByBook(bookService.getBookByIsbn(isbn));
    }

    public List<BookIssuance> getBookIssuancesByBook(Book book) {
        return bookIssuanceRepository.findAllByBook(book).orElseThrow(() -> new NotFoundException("Book Issuance Records from book %s not found".formatted(book.getIsbn())));
    }

    public List<BookIssuance> getBookIssuancesByDateIssued(LocalDate dateIssued) {
        return bookIssuanceRepository.findAllByDateIssued(dateIssued).orElseThrow(() -> new NotFoundException("Book Issuance Record issued on %s not found".formatted(dateIssued)));
    }

    public List<BookIssuance> getBookIssuancesByDateDue(LocalDate dateDue) {
        return bookIssuanceRepository.findAllByDateDue(dateDue).orElseThrow(() -> new NotFoundException("Book Issuance Record due on %s not found".formatted(dateDue)));
    }

    public List<BookIssuance> getBookIssuancesByMemberId(Integer id) {
        return getBookIssuancesByMemberIssuedTo(memberService.getMemberById(id));
    }

    public List<BookIssuance> getBookIssuancesByMemberIssuedTo(Member issuedTo) {
        return bookIssuanceRepository.findAllByIssuedTo(issuedTo).orElseThrow(() -> new NotFoundException("Book Issuance Record issued to %s not found".formatted(issuedTo.getId())));
    }

    public List<BookIssuance> getBookIssuancesByReturnStatus(ReturnStatus returnStatus) {
        return bookIssuanceRepository.findAllByReturnStatus(returnStatus).orElseThrow(() -> new NotFoundException("Book Issuance Record with status %s not found".formatted(returnStatus)));
    }


    public BookIssuance addBookIssuance(BookIssuance bookIssuance) {
        if(bookIssuance.getId() != null && bookIssuanceRepository.existsById(bookIssuance.getId()))
            throw new DuplicateValueException("Book Issuance %s already exists.".formatted(bookIssuance.getId()));

        return bookIssuanceRepository.save(bookIssuance);
    }

    public List<BookIssuance> addBookIssuances(List<BookIssuance> bookIssuances) {
        bookIssuances.forEach(this::addBookIssuance);

        return bookIssuances;
    }


    public BookIssuance updateBookIssuance(BookIssuance newBookIssuance, Integer bookIssuanceId) {
        BookIssuance oldBookIssuance = getBookIssuanceById(bookIssuanceId);

        if(newBookIssuance.getBook() != null && newBookIssuance.getBook().getIsbn() != null)
            oldBookIssuance.setBook(newBookIssuance.getBook());
        if(newBookIssuance.getDateIssued() != null && (newBookIssuance.getDateIssued().isBefore(LocalDate.now()) || newBookIssuance.getDateIssued().isEqual(LocalDate.now())))
            oldBookIssuance.setDateIssued(newBookIssuance.getDateIssued());
        if(newBookIssuance.getDateDue() != null && (newBookIssuance.getDateDue().isBefore(LocalDate.now()) || newBookIssuance.getDateDue().isEqual(LocalDate.now())))
            oldBookIssuance.setDateDue(newBookIssuance.getDateDue());
        if(newBookIssuance.getIssuedTo() != null && newBookIssuance.getIssuedTo().getId() != null)
            oldBookIssuance.setIssuedTo(newBookIssuance.getIssuedTo());
        if(newBookIssuance.getReturnStatus() != null)
            oldBookIssuance.setReturnStatus(newBookIssuance.getReturnStatus());

        return bookIssuanceRepository.save(oldBookIssuance);
    }

    public void deleteBookIssuance(BookIssuance bookIssuance) {
        bookIssuanceRepository.delete(bookIssuance);
    }

    public void deleteBookIssuanceById(Integer id) {
        deleteBookIssuance(getBookIssuanceById(id));
    }

    public void deleteBookIssuancesByBookIsbn(String isbn) {
        getBookIssuancesByBookIsbn(isbn).forEach(this::deleteBookIssuance);
    }

    public void deleteBookIssuancesByBook(Book book) {
        getBookIssuancesByBook(book).forEach(this::deleteBookIssuance);
    }

    public void deleteBookIssuancesByDateIssued(LocalDate dateIssued) {
        getBookIssuancesByDateIssued(dateIssued).forEach(this::deleteBookIssuance);
    }

    public void deleteBookIssuancesByDateDue(LocalDate dateDue) {
        getBookIssuancesByDateDue(dateDue).forEach(this::deleteBookIssuance);
    }

    public void deleteBookIssuancesByMemberId(Integer id) {
        getBookIssuancesByMemberId(id).forEach(this::deleteBookIssuance);
    }

    public void deleteBookIssuancesByMemberIssuedTo(Member issuedTo) {
        getBookIssuancesByMemberIssuedTo(issuedTo).forEach(this::deleteBookIssuance);
    }

    public void deleteBookIssuancesByReturnStatus(ReturnStatus returnStatus) {
        getBookIssuancesByReturnStatus(returnStatus).forEach(this::deleteBookIssuance);
    }

    public void deleteAllBookIssuances() {
        bookIssuanceRepository.deleteAll();
    }
}
