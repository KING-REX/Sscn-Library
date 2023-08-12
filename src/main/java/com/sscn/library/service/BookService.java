package com.sscn.library.service;

import com.sscn.library.entity.Book;
import com.sscn.library.exception.NotFoundException;
import com.sscn.library.repository.BookRepository;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    public Book addBook(Book book){
        return bookRepository.save(book);
    }

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    public Book getBookByIsbn(String isbn){
        return bookRepository.findByIsbn(isbn).orElseThrow(() -> new NotFoundException(String.format("Book with isbn %s is not found.", isbn)));
    }

    public Book updateBook(Book newBook, String isbn) {
        Book oldBook = getBookByIsbn(isbn);

        if(!newBook.getTitle().isEmpty())
            oldBook.setTitle(newBook.getTitle());

        if(newBook.getAvailableCopies() >= 0)
            oldBook.setAvailableCopies(newBook.getAvailableCopies());

        if(newBook.getTotalCopies() >= 0)
            oldBook.setTotalCopies(newBook.getTotalCopies());

        if(newBook.getDatePurchased().isBefore(LocalDate.now()) || newBook.getDatePurchased().isEqual(LocalDate.now()))
            oldBook.setDatePurchased(newBook.getDatePurchased());

        if(!newBook.getAuthors().isEmpty())
            oldBook.setAuthors(newBook.getAuthors());

        return bookRepository.save(oldBook);
    }

    public void deleteBookByIsbn(String isbn){
        bookRepository.deleteByIsbn(isbn);
    }

    public void removeAllBooks(){
        bookRepository.deleteAll();
    }

}
