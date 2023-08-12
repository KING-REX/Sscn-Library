package com.sscn.library.service;

import com.sscn.library.entity.Author;
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

    public Author getAuthorById(int id) {
        return authorRepository.findById(id).orElseThrow(() ->  new NotFoundException(String.format("Author with id %s was not found.", id)));
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author addAuthor(Author author) {
        return authorRepository.save(author);
    }

    public Author updateAuthor(Author newAuthor, int authorId) {
        Author oldAuthor = getAuthorById(authorId);

        oldAuthor.setFirstName(newAuthor.getFirstName());
        oldAuthor.setLastName(newAuthor.getLastName());
        oldAuthor.setBooks(newAuthor.getBooks());

        return authorRepository.save(newAuthor);
    }

    public void removeAuthor(Author author) {
        authorRepository.delete(author);
    }

//    getAuthorById();

//    removeAuthorById();


}
