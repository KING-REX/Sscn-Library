package com.sscn.library.controller;


import com.sscn.library.entity.Author;
import com.sscn.library.service.AuthorService;

import java.util.List;

public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    //For the get Mapping
    public List<Author> getAuthors() {
        return authorService.getAuthors();
    }

    //Prolly post
//    public addAuthor() {
//
//    }
}
