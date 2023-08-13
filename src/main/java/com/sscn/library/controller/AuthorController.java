package com.sscn.library.controller;


import com.sscn.library.entity.Author;
import com.sscn.library.exception.InvalidArgumentException;
import com.sscn.library.service.AuthorService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Author> getAuthorById(@PathVariable Integer id) {
        return ResponseEntity.ok(authorService.getAuthorById(id));
    }

    @GetMapping("/{lastName}.l")
    public ResponseEntity<List<Author>> getAuthorsByLastName(@PathVariable String lastName) {
        return ResponseEntity.ok(authorService.getAuthorsByLastName(lastName));
    }

    @GetMapping("/{fullName}.fl")
    public ResponseEntity<List<Author>> getAuthorsByFullName(@PathVariable String fullName) {
        String[] names = fullName.split("-");
        if(names.length > 2)
            throw new InvalidArgumentException("Syntax for full name is wrong!");
        return ResponseEntity.ok(authorService.getAuthorsByFullName(names[0], names[1]));
    }

    @GetMapping
    public ResponseEntity<List<Author>> getAuthors() {
        return ResponseEntity.ok(authorService.getAllAuthors());
    }

    @PostMapping
    public ResponseEntity<List<Author>> addAuthors(@RequestBody List<Author> authors) {
        return ResponseEntity.ok(authorService.addAuthors(authors));
    }

    @PutMapping("/{authorId}")
    public ResponseEntity<Author>  updateAuthor(@RequestBody Author newAuthor, @PathVariable Integer authorId) {
        return ResponseEntity.ok(authorService.updateAuthor(newAuthor, authorId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity.BodyBuilder removeAuthorById(@PathVariable Integer id) {
        authorService.removeAuthorById(id);
        return ResponseEntity.ok();
    }

    @DeleteMapping("/{lastName}.l")
    public ResponseEntity.BodyBuilder removeAuthorsByLastName(@PathVariable String lastName) {
        authorService.removeAuthorsByLastName(lastName);
        return ResponseEntity.ok();
    }

    @DeleteMapping("/{fullName}.fl")
    public ResponseEntity.BodyBuilder removeAuthorsByFullName(@PathVariable String fullName) {
        String[] names = fullName.split("-");
        if(names.length > 2)
            throw new InvalidArgumentException("Syntax for full name is wrong!");
        authorService.removeAuthorsByFullName(names[0], names[1]);
        return ResponseEntity.ok();
    }

    @DeleteMapping
    public ResponseEntity.BodyBuilder removeAllAuthors() {
        authorService.removeAllAuthors();
        return ResponseEntity.ok();
    }
}
