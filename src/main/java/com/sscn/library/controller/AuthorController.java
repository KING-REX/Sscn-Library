package com.sscn.library.controller;


import com.sscn.library.entity.Author;
import com.sscn.library.service.AuthorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/{id}")
    public Author getAuthorById(@PathVariable int id) {
        return authorService.getAuthorById(id);
    }

    @GetMapping
    public List<Author> getAuthors() {
        return authorService.getAllAuthors();
    }

    @PostMapping
    public Author addAuthor(@RequestBody Author author) {
        return authorService.addAuthor(author);
    }

    @PutMapping
    public Author updateAuthor(@RequestBody Author newAuthor, int authorId) {
        return authorService.updateAuthor(newAuthor, authorId);
    }

    //Prolly post
//    public addAuthor() {
//
//    }
}
