package com.sscn.library.service;

import com.sscn.library.entity.Author;
import com.sscn.library.entity.Book;
import com.sscn.library.exception.DuplicateValueException;
import com.sscn.library.exception.InvalidArgumentException;
import com.sscn.library.exception.NotFoundException;
import com.sscn.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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

        if(book.getTitle() == null)
            throw new InvalidArgumentException("Title cannot be null!");
        if(book.getTitle().isEmpty())
            throw new InvalidArgumentException("Title cannot be empty!");

        if(book.getTotalCopies() == null)
            throw new InvalidArgumentException("Total Copies cannot be null!");
        if(book.getTotalCopies() < 0)
            throw new InvalidArgumentException("Total Copies cannot be less than zero!");

        if(book.getAvailableCopies() == null)
            book.setAvailableCopies(book.getTotalCopies());
        if(book.getAvailableCopies() < 0)
            throw new InvalidArgumentException("Available Copies cannot be less than zero!");
        else if(book.getAvailableCopies() > book.getTotalCopies())
            throw new InvalidArgumentException("Available Copies cannot be more than total copies!");

        if(book.getDatePurchased() == null)
            throw new InvalidArgumentException("Date Purchased attribute cannot be null!");
        if(book.getDatePurchased().isAfter(LocalDate.now()))
            throw new InvalidArgumentException("Date purchased must be less than or equal to the current date!");

        //JSON ignores the "authors" attribute, hence the reason why "authorIds" is used
        //JSON also uses the empty constructor while parsing, meaning "authors"/"authorIds" cannot be null, hence the
        //reason why checking if they're null is skipped
        if(!book.getAuthorIds().isEmpty()) {
            List<Author> authors = new ArrayList<>();
            book.getAuthorIds().forEach((authorId) -> {
                authors.add(authorService.getAuthorById(Integer.parseInt(authorId)));
            });
            book.setAuthors(authors);
            book.setAuthorIds(new ArrayList<>());
        }

        return bookRepository.save(book);
    }

    public List<Book> addBooks(List<Book> books) {
        books.forEach(this::addBook);

        return books;
    }

    public Book updateBook(Book newBook, String isbn) {
        Book oldBook = getBookByIsbn(isbn);

        if(newBook.getTitle() != null) {
            if(newBook.getTitle().isEmpty())
                throw new InvalidArgumentException("Title cannot be empty!");
            oldBook.setTitle(newBook.getTitle());
        }

        if(newBook.getTotalCopies() != null) {
            if(newBook.getTotalCopies() < 0)
                throw new InvalidArgumentException("Total Copies cannot be less than zero!");
            oldBook.setTotalCopies(newBook.getTotalCopies());
        }

        if(newBook.getAvailableCopies() != null) {
            if(newBook.getAvailableCopies() < 0)
                throw new InvalidArgumentException("Available Copies cannot be less than zero!");
            if (newBook.getAvailableCopies() > oldBook.getTotalCopies())
                throw new InvalidArgumentException("Available Copies cannot be more than total copies!");
            oldBook.setAvailableCopies(newBook.getAvailableCopies());
        }

        if(newBook.getDatePurchased() != null) {
            if(newBook.getDatePurchased().isAfter(LocalDate.now()))
                throw new InvalidArgumentException("Date purchased must be less than or equal to the current date!");
            oldBook.setDatePurchased(newBook.getDatePurchased());
        }

        //JSON ignores the "books" attribute, hence the reason why "bookIsbns" is used
        //JSON also uses the empty constructor while parsing, meaning "books"/"bookIsbns" cannot be null, hence the
        //reason why checking if they're null is skipped
        if (!newBook.getAuthorIds().isEmpty()) {
            Set<Author> authors = new LinkedHashSet<>();
            if(newBook.getAuthorIds().get(0).equals("..."))
                authors.addAll(oldBook.getAuthors());
            else if(newBook.getAuthorIds().get(0).equals("-")) {
                List<String> authorsToRemove = newBook.getAuthorIds().subList(1, newBook.getAuthorIds().size());
                authors.addAll(oldBook.getAuthors());
                authors.removeIf(author -> (authorsToRemove.contains(author.getId().toString())));
            }
            newBook.getAuthorIds().forEach((authorId) -> {
                if(!authorId.equals("...") && !newBook.getAuthorIds().contains("-"))
                    authors.add(authorService.getAuthorById(Integer.parseInt(authorId)));
            });
            System.out.println("Authors: " + authors);
            oldBook.setAuthors(List.copyOf(authors));
            oldBook.setAuthorIds(new ArrayList<>());
        }
        else
            oldBook.setAuthors(new ArrayList<>());

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
