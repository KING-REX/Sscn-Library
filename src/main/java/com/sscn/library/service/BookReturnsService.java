package com.sscn.library.service;

import com.sscn.library.entity.Book;
import com.sscn.library.entity.BookIssuance;
import com.sscn.library.entity.BookReturns;
import com.sscn.library.entity.ReturnStatus;
import com.sscn.library.exception.DuplicateValueException;
import com.sscn.library.exception.InvalidArgumentException;
import com.sscn.library.exception.NotFoundException;
import com.sscn.library.repository.BookReturnsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BookReturnsService {
    private final BookReturnsRepository bookReturnsRepository;
    private final BookService bookService;
    private final BookIssuanceService bookIssuanceService;

    public List<BookReturns> getAllBookReturns() {
        return bookReturnsRepository.findAll();
    };

    public BookReturns getBookReturnById(Integer id) {
        return bookReturnsRepository.findById(id).orElseThrow(() -> new NotFoundException("Book Returns with id %s does not exist.".formatted(id)));
    }

    public List<BookReturns> getBookReturnsByBookIssued(Book bookIssued) {
        return bookReturnsRepository.findAllByBookReturned(bookIssued).orElseThrow(() -> new NotFoundException("Book Returns for book %s does not exist.".formatted(bookIssued.getIsbn())));
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

    public List<BookReturns> getBookReturnsByReturnStatus(ReturnStatus returnStatus) {
        return bookReturnsRepository.findAllByReturnStatus(returnStatus).orElseThrow(() -> new NotFoundException("Book Returns with status %s does not exist.".formatted(returnStatus)));
    }

    public List<BookReturns> getBookReturnsByDateReturned(LocalDate dateReturned) {
        return bookReturnsRepository.findAllByDateReturned(dateReturned).orElseThrow(() -> new NotFoundException("Book Returns on %s does not exist.".formatted(dateReturned)));
    }

    public BookReturns addBookReturns(BookReturns bookReturns) {
        if(bookReturns.getId() != null && bookReturnsRepository.existsById(bookReturns.getId()))
            throw new DuplicateValueException("Book Returns %s already exists".formatted(bookReturns.getId()));
        else if(bookReturns.getId() != null)
            throw new InvalidArgumentException("Book Returns Id is auto-generated. Don't give it a value!");

        if(bookReturns.getBookReturned() == null) {
            if(bookReturns.getBookIsbn() != null && !bookReturns.getBookIsbn().isEmpty())
                bookReturns.setBookReturned(bookService.getBookByIsbn(bookReturns.getBookIsbn()));
            else
                throw new InvalidArgumentException("Book Issued Isbn is invalid!");
        }

        if(bookReturns.getBookIssuance() == null) {
            if(bookReturns.getBookIssuanceId() != null)
                bookReturns.setBookIssuance(bookIssuanceService.getBookIssuanceById(bookReturns.getBookIssuanceId()));
            else
                throw new InvalidArgumentException("Book Issuance Id is null!");
        }

        if(bookReturns.getDateReturned() == null)
            throw new InvalidArgumentException("Date returned attribute cannot be null!");

        return bookReturnsRepository.save(bookReturns);
    }

    public List<BookReturns> addBookReturns(List<BookReturns> bookReturns) {
        bookReturns.forEach(this::addBookReturns);

        return bookReturns;
    }

    public BookReturns updateBookReturns(BookReturns newBookReturns, Integer id) {
        BookReturns oldBookReturns = getBookReturnById(id);

        if(newBookReturns.getBookReturned() != null && newBookReturns.getBookReturned().getIsbn() != null)
            oldBookReturns.setBookReturned(newBookReturns.getBookReturned());
        else if(newBookReturns.getBookIsbn() != null && !newBookReturns.getBookIsbn().isEmpty())
            oldBookReturns.setBookReturned(bookService.getBookByIsbn(newBookReturns.getBookIsbn()));

        if(newBookReturns.getBookIssuance() != null && newBookReturns.getBookIssuance().getId() != null)
            oldBookReturns.setBookIssuance(newBookReturns.getBookIssuance());
        else if(newBookReturns.getReturnsBookIssuanceId() != null)
            oldBookReturns.setBookIssuance(bookIssuanceService.getBookIssuanceById(newBookReturns.getReturnsBookIssuanceId()));

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

    public void deleteBookReturnsByReturnStatus(ReturnStatus returnStatus) {
        getBookReturnsByReturnStatus(returnStatus).forEach(this::deleteBookReturns);
    };

    public void deleteAllBookReturns() {
        bookReturnsRepository.deleteAll();
    }
}