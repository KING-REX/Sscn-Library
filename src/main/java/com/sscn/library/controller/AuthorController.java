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
    public Author getAuthorById(@PathVariable Integer id) {
        return authorService.getAuthorById(id);
    }

    @GetMapping("/{lastName}.l")
    public List<Author> getAuthorsByLastName(@PathVariable String lastName) {
        return authorService.getAuthorsByLastName(lastName);
    }

    @GetMapping("/{firstName}-{lastName}.fl")
    public List<Author> getAuthorsByFullName(@PathVariable String firstName, @PathVariable String lastName) {
        return authorService.getAuthorsByFullName(firstName, lastName);
    }

    @GetMapping
    public List<Author> getAuthors() {
        return authorService.getAllAuthors();
    }

    @PostMapping
    public List<Author> addAuthors(@RequestBody List<Author> authors) {
        return authorService.addAuthors(authors);
    }

    @PutMapping
    public Author updateAuthor(@RequestBody Author newAuthor, @PathVariable Integer authorId) {
        return authorService.updateAuthor(newAuthor, authorId);
    }

    @DeleteMapping("/{id}")
    public void removeAuthorById(@PathVariable Integer id) {
        authorService.removeAuthorById(id);
    }

    @DeleteMapping("/{lastName}.l")
    public void removeAuthorsByLastName(@PathVariable String lastName) {
        authorService.removeAuthorsByLastName(lastName);
    }

    @DeleteMapping("/{firstName}-{lastName}.fl")
    public void removeAuthorsByFullName(@PathVariable String firstName, @PathVariable String lastName) {
        authorService.removeAuthorsByFullName(firstName, lastName);
    }

    @DeleteMapping
    public void removeAllAuthors() {
        authorService.removeAllAuthors();
    }
}
