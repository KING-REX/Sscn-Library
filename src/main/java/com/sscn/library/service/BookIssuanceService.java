package com.sscn.library.service;

import com.sscn.library.entity.Book;
import com.sscn.library.entity.BookIssuance;
import com.sscn.library.entity.Member;
import com.sscn.library.entity.BorrowStatus;
import com.sscn.library.exception.DuplicateValueException;
import com.sscn.library.exception.InvalidArgumentException;
import com.sscn.library.exception.NotFoundException;
import com.sscn.library.repository.BookIssuanceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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
//        List<BookIssuance> issuancesByBookIsbn = new ArrayList<>();
//        getAllBookIssuances().stream().filter((issuance) -> issuance.getBookIssued().getIsbn().equals(book.getIsbn())).forEach(issuancesByBookIsbn::add);
        return bookIssuanceRepository.findAllByBookIssued(book).orElseThrow(() -> new NotFoundException("Book Issuance Record for book %s not found".formatted(book.getIsbn())));
//        return issuancesByBookIsbn;
    }

    public List<BookIssuance> getBookIssuancesByDateIssued(LocalDate dateIssued) {
        return bookIssuanceRepository.findAllByDateIssued(dateIssued).orElseThrow(() -> new NotFoundException("Book Issuance Record issued on %s not found".formatted(dateIssued)));
//        return List.of(bookIssuanceRepository.findByDateIssued(dateIssued).orElseThrow(() -> new NotFoundException("Book Issuance Record issued on %s not found".formatted(dateIssued))));
    }

    public List<BookIssuance> getBookIssuancesByDateDue(LocalDate dateDue) {
        return bookIssuanceRepository.findAllByDateDue(dateDue).orElseThrow(() -> new NotFoundException("Book Issuance Record due on %s not found".formatted(dateDue)));
    }

    public List<BookIssuance> getBookIssuancesByMemberId(Integer id) {
        return getBookIssuancesByMemberIssuedTo(memberService.getMemberById(id));
    }

    public List<BookIssuance> getBookIssuancesByMemberIssuedTo(Member issuedTo) {
//        List<BookIssuance> issuancesByMemberId = new ArrayList<>();
//        getAllBookIssuances().stream().filter((issuance) -> issuance.getIssuedTo().getId().equals(issuedTo.getId())).forEach(issuancesByMemberId::add);
        return bookIssuanceRepository.findAllByIssuedTo(issuedTo).orElseThrow(() -> new NotFoundException("Book Issuance Record for member %s not found".formatted(issuedTo.getId())));
//        return issuancesByMemberId;
    }
    
    public List<BookIssuance> getBookIssuancesByCopiesIssued(Integer copiesIssued) {
        return bookIssuanceRepository.findAllByCopiesIssued(copiesIssued).orElseThrow(() -> new NotFoundException("Book Issuance with %s copies not found".formatted(copiesIssued)));
//        return null;
    }

    public List<BookIssuance> getBookIssuancesByBorrowStatus(BorrowStatus borrowStatus) {
        return bookIssuanceRepository.findAllByBorrowStatus(borrowStatus).orElseThrow(() -> new NotFoundException("Book Issuance Record with status %s not found".formatted(borrowStatus)));
    }


    public BookIssuance addBookIssuance(BookIssuance bookIssuance) {
        if(bookIssuance.getId() != null && bookIssuanceRepository.existsById(bookIssuance.getId()))
            throw new DuplicateValueException("Book Issuance %s already exists.".formatted(bookIssuance.getId()));
        else if(bookIssuance.getId() != null)
            throw new InvalidArgumentException("Book Issuance Id is auto-generated. Don't give it a value!");

        if(bookIssuance.getBookIsbn() != null) {
            if(bookIssuance.getBookIsbn().isEmpty())
                throw new InvalidArgumentException("Book ISBN cannot be empty!");
            bookIssuance.setBookIssued(bookService.getBookByIsbn(bookIssuance.getBookIsbn()));
        }
        else
            throw new InvalidArgumentException("Book ISBN cannot be null!");

        if(bookIssuance.getDateIssued() == null)
//            throw new InvalidArgumentException("Date issued cannot be null!");
            bookIssuance.setDateIssued(LocalDate.now());
        else if(bookIssuance.getDateIssued().isAfter(LocalDate.now()))
            throw new InvalidArgumentException("Date issued must be less than or equal to current date!");

        if(bookIssuance.getDateDue() == null)
            throw new InvalidArgumentException("Date due cannot be null!");
        else if(bookIssuance.getDateDue().isBefore(bookIssuance.getDateIssued()))
            throw new InvalidArgumentException("Date due must be more than or equal to date issued!");
        else if(bookIssuance.getDateDue().isBefore(LocalDate.now()))
            throw new InvalidArgumentException("Date due must be more than or equal to current date!");

        if(bookIssuance.getMemberId() != null) {
            if(bookIssuance.getMemberId() < 0)
                throw new InvalidArgumentException("Member id cannot be less than zero!");
            bookIssuance.setIssuedTo(memberService.getMemberById(bookIssuance.getMemberId()));
        }
        else
            throw new InvalidArgumentException("Member attribute cannot be null!");
        
        if(bookIssuance.getCopiesIssued() != null) {
            if(bookIssuance.getCopiesIssued() <= 0)
                throw new InvalidArgumentException("Copies issued must be greater than zero!");
        }
        else
            bookIssuance.setCopiesIssued(1);

        if(bookIssuance.getBorrowStatus() == null)
            bookIssuance.setBorrowStatus(BorrowStatus.BORROWED);
        else
            throw new InvalidArgumentException("Borrow Status should not be specified. It is \"BORROWED\" by default!");

        evaluateBookAvailableCopies(bookIssuance, "add");
        return bookIssuanceRepository.save(bookIssuance);
//        return null;
    }

    public List<BookIssuance> addBookIssuances(List<BookIssuance> bookIssuances) {
        bookIssuances.forEach(this::addBookIssuance);

        return bookIssuances;
    }

    public BookIssuance updateBookIssuance(BookIssuance newBookIssuance, Integer bookIssuanceId) throws CloneNotSupportedException {
        BookIssuance oldBookIssuance = getBookIssuanceById(bookIssuanceId);

        if(newBookIssuance.getBookIsbn() != null) {
            if(newBookIssuance.getBookIsbn().isEmpty())
                throw new InvalidArgumentException("Book ISBN cannot be empty!");
            oldBookIssuance.setBookIssued(bookService.getBookByIsbn(newBookIssuance.getBookIsbn()));
        }

        if(newBookIssuance.getDateIssued() != null) {
            if(newBookIssuance.getDateIssued().isAfter(LocalDate.now()))
                throw new InvalidArgumentException("Date issued must be less than or equal to current date!");
            oldBookIssuance.setDateIssued(newBookIssuance.getDateIssued());
        }

        if(newBookIssuance.getDateDue() != null) {
            if(newBookIssuance.getDateDue().isBefore(newBookIssuance.getDateIssued()))
                throw new InvalidArgumentException("Date due must be more than or equal to date issued!");
            else if(newBookIssuance.getDateDue().isBefore(LocalDate.now()))
                throw new InvalidArgumentException("Date due must be more than or equal to current date!");
            oldBookIssuance.setDateDue(newBookIssuance.getDateDue());
        }

        if(newBookIssuance.getMemberId() != null) {
            if(newBookIssuance.getMemberId() < 0)
                throw new InvalidArgumentException("Member id cannot be less than zero!");
            oldBookIssuance.setIssuedTo(memberService.getMemberById(newBookIssuance.getMemberId()));
        }
        
        if(newBookIssuance.getCopiesIssued() != null) {
            if(newBookIssuance.getCopiesIssued() <= 0)
                throw new InvalidArgumentException("Copies issued must be greater than zero!");

            BookIssuance tempOldBookIssuance = oldBookIssuance.clone();
            tempOldBookIssuance.setCopiesIssued(newBookIssuance.getCopiesIssued());
            evaluateBookAvailableCopies(tempOldBookIssuance, "update");
            oldBookIssuance.setCopiesIssued(newBookIssuance.getCopiesIssued());
        }

        if(newBookIssuance.getBorrowStatus() != null)
            oldBookIssuance.setBorrowStatus(newBookIssuance.getBorrowStatus());

        return bookIssuanceRepository.save(oldBookIssuance);
    }

    public boolean deleteBookIssuance(BookIssuance bookIssuance) {
        System.out.println("BookIssuance to be deleted: " + bookIssuance);
        bookIssuanceRepository.delete(bookIssuance);
        evaluateBookAvailableCopies(bookIssuance, "delete");
        return true;
    }

    public boolean deleteBookIssuanceById(Integer id) {
        return deleteBookIssuance(getBookIssuanceById(id));
    }

    public boolean deleteBookIssuancesByBookIsbn(String isbn) {
        getBookIssuancesByBookIsbn(isbn).forEach(this::deleteBookIssuance);
        return true;
    }

    public boolean deleteBookIssuancesByBook(Book book) {
        getBookIssuancesByBook(book).forEach(this::deleteBookIssuance);
        return true;
    }

    public boolean deleteBookIssuancesByDateIssued(LocalDate dateIssued) {
        getBookIssuancesByDateIssued(dateIssued).forEach(this::deleteBookIssuance);
        return true;
    }

    public boolean deleteBookIssuancesByDateDue(LocalDate dateDue) {
        getBookIssuancesByDateDue(dateDue).forEach(this::deleteBookIssuance);
        return true;
    }

    public boolean deleteBookIssuancesByMemberId(Integer id) {
        System.out.println("Book Issuances to be deleted: " + getBookIssuancesByMemberId(id));
        System.out.println("Member Id: " + id);
        getBookIssuancesByMemberId(id).forEach(this::deleteBookIssuance);
        return true;
    }

    public boolean deleteBookIssuancesByMemberIssuedTo(Member issuedTo) {
        getBookIssuancesByMemberIssuedTo(issuedTo).forEach(this::deleteBookIssuance);
        return true;
    }
    
    public boolean deleteBookIssuancesByCopiesIssued(Integer copiesIssued) {
        getBookIssuancesByCopiesIssued(copiesIssued).forEach(this::deleteBookIssuance);
        return true;
    }

    public boolean deleteBookIssuancesByBorrowStatus(BorrowStatus borrowStatus) {
        getBookIssuancesByBorrowStatus(borrowStatus).forEach(this::deleteBookIssuance);
        return true;
    }

    public boolean deleteAllBookIssuances() {
        getAllBookIssuances().forEach(this::deleteBookIssuance);
        //COMPLETED: Evaluate book available copies for all the records deleted and return them to normal!
        return true;
    }

    public void evaluateBookAvailableCopies(BookIssuance bookIssuance, String from) {
        Book bookToBeEdited;
        if(bookIssuance.getBookIssued() != null)
            bookToBeEdited = bookService.getBookByIsbn(bookIssuance.getBookIssued().getIsbn());
        else if(bookIssuance.getBookIsbn() != null)
            bookToBeEdited = bookService.getBookByIsbn(bookIssuance.getBookIsbn());
        else
            throw new InvalidArgumentException("Book doesn't exist!");

        switch(from) {
            case "add" -> {
                if(bookToBeEdited.getAvailableCopies() >= bookIssuance.getCopiesIssued())
                    bookToBeEdited.setAvailableCopies(bookToBeEdited.getAvailableCopies() - bookIssuance.getCopiesIssued());
                else
                    throw new InvalidArgumentException("Book %s doesn't have enough available copies for this issuance!".formatted(bookToBeEdited.getIsbn()));
            }
            case "update" -> {
                var previousCopiesIssued = getBookIssuanceById(bookIssuance.getId()).getCopiesIssued();
//                if("a".equals("a"))
//                    return;
                if(previousCopiesIssued.equals(bookIssuance.getCopiesIssued()))
                    return;
                var difference = previousCopiesIssued - bookIssuance.getCopiesIssued();
                if(difference < 0) {
                    bookIssuance.setCopiesIssued(Math.abs(difference));
                    evaluateBookAvailableCopies(bookIssuance, "add");
                }
                else {
                    bookIssuance.setCopiesIssued(Math.abs(difference));
                    evaluateBookAvailableCopies(bookIssuance, "delete");
                }
            }
            case "delete" -> {
                if(bookToBeEdited.getTotalCopies() >= bookToBeEdited.getAvailableCopies() + bookIssuance.getCopiesIssued())
                    bookToBeEdited.setAvailableCopies(bookToBeEdited.getAvailableCopies() + bookIssuance.getCopiesIssued());
                else
                    throw new UnsupportedOperationException("Releasing all copies will make available copies higher than total copies which isn't practicable."
                            + " Hence, the reason why this operation is invalidated!");
            }
            default -> {
                throw new InvalidArgumentException("\"From\" argument is invalid!");
            }
        }
        //COMPLETED: Test the evaluate available copies for the same book when more than one bookIssuance record is added for it
        bookService.updateBook(bookToBeEdited, bookToBeEdited.getIsbn());
    }

    //TODO: Add a quartz scheduler to determine when a book issuance's borrow status is "overdue"!. Make sure that if the book issuance's borrow status is
    // "returned", the scheduler should no longer have any effect on the record. Hence, only "borrowed" records can be "overdue".
}
