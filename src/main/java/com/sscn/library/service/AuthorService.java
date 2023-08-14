package com.sscn.library.service;

import com.sscn.library.entity.Author;
import com.sscn.library.entity.Book;
import com.sscn.library.exception.DuplicateValueException;
import com.sscn.library.exception.NotFoundException;
import com.sscn.library.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final BookService bookService;

//    @Autowired
    public AuthorService(AuthorRepository authorRepository, BookService bookService) {
        this.authorRepository = authorRepository;
        this.bookService = bookService;
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author getAuthorById(Integer id) {
        return authorRepository.findById(id).orElseThrow(() ->  new NotFoundException(String.format("Author with id %s was not found.", id)));
    }

    public List<Author> getAuthorsByLastName(String lastName) {
        return authorRepository.findAllByLastName(lastName).orElseThrow(() -> new NotFoundException("No author with the name %s exists.".formatted(lastName)));
    }

    public List<Author> getAuthorsByFullName(String firstName, String lastName) {

//        return authorRepository
//                .findAllByFirstNameAndLastName(firstName, lastName)
//                .orElseThrow(() -> new NotFoundException("No author with the name %s %s exists.".formatted(firstName, lastName)));

//        List<Author> fullAuthorList = authorRepository
//                .findAllByFirstNameAndLastName(firstName, lastName)
//                .or(() -> authorRepository.findAllByLastNameAndFirstName(firstName, lastName))
//                .orElseThrow(() -> new NotFoundException("No author with the name %s %s exists.".formatted(firstName, lastName)));
////                .orElseGet(() -> revAuthorList);
//////                .orElseThrow(() -> new NotFoundException("No author with the name %s %s exists.".formatted(firstName, lastName)));


        //TODO: Make this method also search in reverse (i.e. ("John", "Doe") should return the same results as ("Doe", "John").
        // Update: This method now works. The only problem is that the "or"/"orElse" part of the function is meant to be handled by the Optional like
        // the above commented fullAuthorList(although it's not working), not manually
        List<Author> revAuthorList = authorRepository
                .findAllByLastNameAndFirstName(firstName, lastName)
                .orElseThrow(() -> new NotFoundException("No author with the name %s %s exists.".formatted(firstName, lastName)));
        List<Author> authorList = authorRepository
                .findAllByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(() -> new NotFoundException("No author with the name %s %s exists.".formatted(firstName, lastName)));

        if(!authorList.isEmpty()) {
            return authorList;
        }
        if(!revAuthorList.isEmpty())
            return revAuthorList;

        return authorList;
    }

    public Author addAuthor(Author author) {
        if(author.getId() != null && authorRepository.existsById(author.getId()))
            throw new DuplicateValueException("Author %s already exists.".formatted(author.getId()));

        return authorRepository.save(author);
    }

    public List<Author> addAuthors(List<Author> authors) {
        authors.forEach(this::addAuthor);

        return authors;
    }

    public Author updateAuthor(Author newAuthor, Integer authorId) {
        Author oldAuthor = getAuthorById(authorId);

        System.out.println(oldAuthor);

        if(newAuthor.getFirstName() != null && !newAuthor.getFirstName().isEmpty())
            oldAuthor.setFirstName(newAuthor.getFirstName());

        if(newAuthor.getLastName() != null && !newAuthor.getLastName().isEmpty())
            oldAuthor.setLastName(newAuthor.getLastName());

        if(newAuthor.getBooks() != null && !newAuthor.getBooks().isEmpty())
            oldAuthor.setBooks(newAuthor.getBooks());
        else if(newAuthor.getBookIsbns() != null && !newAuthor.getBookIsbns().isEmpty()) {
            List<Book> books = new ArrayList<>();
            newAuthor.getBookIsbns().forEach((bookIsbn) -> {
                books.add(bookService.getBookByIsbn(bookIsbn));
            });
            oldAuthor.setBooks(books);
        }


        return authorRepository.save(oldAuthor);
    }

    public void removeAuthor(Author author) {
        authorRepository.delete(author);
    }

    public void removeAuthorById(Integer id) {

        removeAuthor(getAuthorById(id));

//        authorRepository.deleteById(id);
    }

    public void removeAuthorsByLastName(String lastName) {
        getAuthorsByLastName(lastName).forEach(this::removeAuthor);
    }

    public void removeAuthorsByFullName(String firstName, String lastName) {
        getAuthorsByFullName(firstName, lastName).forEach(this::removeAuthor);
    }

    public void removeAllAuthors() {
        authorRepository.deleteAll();
    }

}
