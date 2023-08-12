package com.sscn.library.service;

import com.sscn.library.entity.Author;
import com.sscn.library.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;

public class AuthorService {

    private final AuthorRepository authorRepository;

//    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> getAuthors() {
        return authorRepository.findAll();
    }

    public void addNewAuthor(Author author){
        authorRepository.save(author);
    }

    public Optional<Author> getAuthorById(String firstName, String lastName){
        return authorRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    public Optional<Author> getAuthorById(int id){
        return authorRepository.findById(id);
    }

    public void removeAuthorById(int id){
        authorRepository.deleteById(id);
    }
}
