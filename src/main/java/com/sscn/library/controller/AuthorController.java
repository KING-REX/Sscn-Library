package com.sscn.library.controller;


import com.sscn.library.entity.Author;
import com.sscn.library.exception.InvalidArgumentException;
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

    @GetMapping("/{fullName}.fl")
    public List<Author> getAuthorsByFullName(@PathVariable String fullName) {
        String[] names = fullName.split("-");
        if(names.length > 2)
            throw new InvalidArgumentException("Syntax for full name is wrong!");
        return authorService.getAuthorsByFullName(names[0], names[1]);
    }

    @GetMapping
    public List<Author> getAuthors() {
        return authorService.getAllAuthors();
    }

    @PostMapping
    public List<Author> addAuthors(@RequestBody List<Author> authors) {
        return authorService.addAuthors(authors);
    }

    @PutMapping("/{authorId}")
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

    @DeleteMapping("/{fullName}.fl")
    public void removeAuthorsByFullName(@PathVariable String fullName) {
        String[] names = fullName.split("-");
        if(names.length > 2)
            throw new InvalidArgumentException("Syntax for full name is wrong!");
        authorService.removeAuthorsByFullName(names[0], names[1]);
    }

    @DeleteMapping
    public void removeAllAuthors() {
        authorService.removeAllAuthors();
    }
}
