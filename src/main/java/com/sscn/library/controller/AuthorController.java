package com.sscn.library.controller;


import com.sscn.library.entity.Author;
import com.sscn.library.exception.DuplicateValueException;
import com.sscn.library.exception.InvalidArgumentException;
import com.sscn.library.exception.NotFoundException;
import com.sscn.library.service.AuthorService;
import jakarta.validation.constraints.Null;
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
    public ResponseEntity<Author> getAuthorById(@PathVariable Integer id) throws NotFoundException {
        return ResponseEntity.ok(authorService.getAuthorById(id));
    }

    @GetMapping("/{lastName}.l")
    public ResponseEntity<List<Author>> getAuthorsByLastName(@PathVariable String lastName) throws NotFoundException {
        return ResponseEntity.ok(authorService.getAuthorsByLastName(lastName));
    }

    @GetMapping("/{fullName}.fl")
    public ResponseEntity<List<Author>> getAuthorsByFullName(@PathVariable String fullName) throws NotFoundException {
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
    public ResponseEntity<List<Author>> addAuthors(@RequestBody List<Author> authors) throws DuplicateValueException, IllegalStateException, IllegalArgumentException {
        return ResponseEntity.ok(authorService.addAuthors(authors));
    }

    @PutMapping("/{authorId}")
    public ResponseEntity<Author> updateAuthor(@RequestBody Author newAuthor, @PathVariable Integer authorId) throws NotFoundException, IllegalArgumentException {
        System.out.println("New" + newAuthor);
        return ResponseEntity.ok(authorService.updateAuthor(newAuthor, authorId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> removeAuthorById(@PathVariable Integer id) throws IllegalArgumentException, NotFoundException {
        authorService.removeAuthorById(id);
        return ResponseEntity.ok(true);
    }

    //COMPLETED: Complete the edition of the delete mappings for the controllers. They should not return a ResponseEntity.BodyBuilder
    // instead they should return a ResponseEntity with a true value or an exception.
    @DeleteMapping("/{lastName}.l")
    public ResponseEntity<Boolean> removeAuthorsByLastName(@PathVariable String lastName) throws IllegalArgumentException, NotFoundException {
        authorService.removeAuthorsByLastName(lastName);
        return ResponseEntity.ok(true);
    }

    @DeleteMapping("/{fullName}.fl")
    public ResponseEntity<Boolean> removeAuthorsByFullName(@PathVariable String fullName) throws IllegalArgumentException, NotFoundException {
        String[] names = fullName.split("-");
        if(names.length > 2)
            throw new InvalidArgumentException("Syntax for full name is wrong!");
        authorService.removeAuthorsByFullName(names[0], names[1]);
        return ResponseEntity.ok(true);
    }

    @DeleteMapping
    public ResponseEntity<Boolean> removeAllAuthors() {
        authorService.removeAllAuthors();
        return ResponseEntity.ok(true);
    }
}
