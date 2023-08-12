package com.sscn.library.service;

import com.sscn.library.entity.Book;
import com.sscn.library.repository.BookRepository;

import java.util.List;
import java.util.Optional;

public class BookService {

    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    public void addBook(Book book){
        bookRepository.save(book);
    }
    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(int id){
        return bookRepository.findById(id);
    }

    public void deleteBookById(int id){
        bookRepository.deleteById(id);
    }

    public void removeAllBooks(){
        bookRepository.deleteAll();
    }

}
