package com.sscn.library.service;

import com.sscn.library.entity.Author;
import com.sscn.library.entity.Book;
import com.sscn.library.exception.DuplicateValueException;
import com.sscn.library.exception.NotFoundException;
import com.sscn.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;

    @Autowired
    public BookService(BookRepository bookRepository, @Lazy AuthorService authorService){
        this.bookRepository = bookRepository;
        this.authorService = authorService;
    }

    public List<Book> getAllBooks(){

        return bookRepository.findAll();
    }

    public Book getBookByIsbn(String isbn){
        return bookRepository.findByIsbn(isbn).orElseThrow(() -> new NotFoundException(String.format("No book with isbn %s exists.", isbn)));
    }

    public List<Book> getBooksByTitle(String title) {
        return bookRepository.findAllByTitle(title).orElseThrow(() -> new NotFoundException("No book titled %s exists.".formatted(title)));
    }

    public List<Book> getBooksByDatePurchased(LocalDate datePurchased) {
        return bookRepository.findAllByDatePurchased(datePurchased).orElseThrow(() -> new NotFoundException("No book was purchased on %s.".formatted(datePurchased)));
    }

    public Book addBook(Book book){
        if(book.getIsbn() != null && bookRepository.existsByIsbn(book.getIsbn()))
            throw new DuplicateValueException("Book %s already exists.".formatted(book.getIsbn()));

        return bookRepository.save(book);
    }

    public List<Book> addBooks(List<Book> books) {
        books.forEach(this::addBook);

        return books;
    }

    public Book updateBook(Book newBook, String isbn) {
        Book oldBook = getBookByIsbn(isbn);

        if(newBook.getTitle() != null && !newBook.getTitle().isEmpty())
            oldBook.setTitle(newBook.getTitle());

        if(newBook.getAvailableCopies() != null && newBook.getAvailableCopies() >= 0)
            oldBook.setAvailableCopies(newBook.getAvailableCopies());

        if(newBook.getTotalCopies() != null && newBook.getTotalCopies() >= 0)
            oldBook.setTotalCopies(newBook.getTotalCopies());

        if(newBook.getDatePurchased() != null && (newBook.getDatePurchased().isBefore(LocalDate.now()) || newBook.getDatePurchased().isEqual(LocalDate.now())))
            oldBook.setDatePurchased(newBook.getDatePurchased());

        if(newBook.getAuthors() != null && !newBook.getAuthors().isEmpty())
            oldBook.setAuthors(newBook.getAuthors());
        else if(newBook.getAuthorIds() != null && !newBook.getAuthorIds().isEmpty()) {
            List<Author> authors = new ArrayList<>();
            newBook.getAuthorIds().forEach((authorId) -> {
                authors.add(authorService.getAuthorById(authorId));
            });
            oldBook.setAuthors(authors);

        }

        return bookRepository.save(oldBook);
    }

    public void deleteBook(Book book) {
        if(!bookRepository.existsByIsbn(book.getIsbn()))
            throw new NotFoundException("Cannot delete book %s because it doesn't exist.".formatted(book.getIsbn()));

        bookRepository.delete(book);
    }

    public void deleteBookByIsbn(String isbn){

        deleteBook(getBookByIsbn(isbn));

//        bookRepository.deleteByIsbn(isbn);
    }

    public void deleteAllBooks(){
        bookRepository.deleteAll();
    }

    public void deleteBooksByTitle(String title) {

        getBooksByTitle(title).forEach(this::deleteBook);

//        bookRepository.deleteAllByTitle(title);
    }

    public void deleteBooksByDatePurchased(LocalDate datePurchased) {
        getBooksByDatePurchased(datePurchased).forEach(this::deleteBook);
    }
}
