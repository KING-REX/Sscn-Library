package com.sscn.library.controller;


import com.sscn.library.entity.Author;
import com.sscn.library.service.AuthorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

//    @GetMapping("/{id}")
//    public Author getAuthorById(@PathVariable int id) {
//        return authorService.getAuthorById(id);
//    }

    @GetMapping
    public List<Author> getAuthors() {
        return authorService.getAuthors();
    }

    //Prolly post
//    public addAuthor() {
//
//    }
}
