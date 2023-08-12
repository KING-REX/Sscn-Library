package com.sscn.library.service;

import com.sscn.library.entity.Author;
import com.sscn.library.exception.DuplicateValueException;
import com.sscn.library.exception.NotFoundException;
import com.sscn.library.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

//    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author getAuthorById(Integer id) {
        return authorRepository.findById(id).orElseThrow(() ->  new NotFoundException(String.format("Author with id %s was not found.", id)));
    }

    public List<Author> getAuthorsByFullName(String firstName, String lastName) {
        return authorRepository.findAllByFirstNameAndLastName(firstName, lastName).orElseThrow(() -> new NotFoundException("No author with the name %s %s exists.".formatted(firstName, lastName)));
    }

    public Author addAuthor(Author author) {
        if(authorRepository.existsById(author.getId()))
            throw new DuplicateValueException("Author %s already exists.".formatted(author.getId()));

        return authorRepository.save(author);
    }

    public List<Author> addAuthors(List<Author> authors) {
        authors.forEach(this::addAuthor);

        return authors;
    }

    public Author updateAuthor(Author newAuthor, Integer authorId) {
        Author oldAuthor = getAuthorById(authorId);

        if(newAuthor.getFirstName() != null && !newAuthor.getFirstName().isEmpty())
            oldAuthor.setFirstName(newAuthor.getFirstName());

        if(newAuthor.getLastName() != null && !newAuthor.getLastName().isEmpty())
            oldAuthor.setLastName(newAuthor.getLastName());

        if(newAuthor.getBooks() != null && !newAuthor.getBooks().isEmpty())
            oldAuthor.setBooks(newAuthor.getBooks());

        return authorRepository.save(newAuthor);
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

    public List<Author> getAuthorsByLastName(String lastName) {
        return authorRepository.findAllByLastName(lastName).orElseThrow(() -> new NotFoundException("No author with the name %s exists.".formatted(lastName)));
    }

}
