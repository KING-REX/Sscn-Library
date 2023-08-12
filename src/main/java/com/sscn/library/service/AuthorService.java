package com.sscn.library.service;

import com.sscn.library.entity.Author;
import com.sscn.library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AuthorService {

    private final AuthorRepository authorRepository;

//    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> getAuthors() {
        return authorRepository.findAll();
    }

//    getAuthorById();

//    removeAuthorById();


}
