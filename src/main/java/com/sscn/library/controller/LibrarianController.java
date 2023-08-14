package com.sscn.library.controller;

import com.sscn.library.entity.BookReturns;
import com.sscn.library.entity.Librarian;
import com.sscn.library.exception.DuplicateValueException;
import com.sscn.library.exception.InvalidArgumentException;
import com.sscn.library.exception.NotFoundException;
import com.sscn.library.service.LibrarianService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/librarian")
public class LibrarianController {

    private final LibrarianService librarianService;

    public LibrarianController(LibrarianService librarianService){
        this.librarianService = librarianService;
    }



    @GetMapping
    public ResponseEntity<List<Librarian>> getAllLibrarians() {
        return ResponseEntity.ok(librarianService.getAllLibrarians());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Librarian> getLibrarianById(@PathVariable Integer id) throws NotFoundException {
        return ResponseEntity.ok(librarianService.getLibrarianById(id));
    }

    @GetMapping("/{lastName}.l")
    public ResponseEntity<List<Librarian>> getLibrariansByLastName(@PathVariable String lastName) throws NotFoundException {
        return ResponseEntity.ok(librarianService.getLibrariansByLastName(lastName));
    }

    @GetMapping("/{fullName}.fl")
    public ResponseEntity<List<Librarian>> getLibrariansByFullName(@PathVariable String fullName) throws NotFoundException {
        String[] names = fullName.split("-");
        if(names.length > 2)
            throw new InvalidArgumentException("Syntax for full name is wrong!");

        return ResponseEntity.ok(librarianService.getLibrariansByFullName(names[0], names[1]));
    }

    @GetMapping("/{email}.e")
    public ResponseEntity<Librarian> getLibrarianByEmail(@PathVariable String email) throws NotFoundException {
        return ResponseEntity.ok(librarianService.getLibrarianByEmail(email));
    }

    @PostMapping
    public ResponseEntity<List<Librarian>>  addLibrarians(@RequestBody List<Librarian> librarians) throws DuplicateValueException, IllegalStateException, IllegalArgumentException {
        return ResponseEntity.ok(librarianService.addLibrarians(librarians));
    }

    @PutMapping("/{librarianId}")
    public ResponseEntity<Librarian>  updateLibrarian(@RequestBody Librarian newLibrarian, @PathVariable Integer librarianId) throws NotFoundException, IllegalArgumentException {
        return ResponseEntity.ok(librarianService.updateLibrarian(newLibrarian, librarianId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity.BodyBuilder deleteLibrarianById(@PathVariable Integer id) throws IllegalArgumentException, NotFoundException {
        librarianService.deleteLibrarianById(id);
        return ResponseEntity.ok();
    }

    @DeleteMapping("/{lastName}.l")
    public ResponseEntity.BodyBuilder deleteLibrariansByLastName(@PathVariable String lastName) throws IllegalArgumentException, NotFoundException {
        librarianService.deleteLibrarianByLastName(lastName);
        return ResponseEntity.ok();
    }

    @DeleteMapping("/{fullName}.fl")
    public ResponseEntity.BodyBuilder deleteLibrariansByFullName(@PathVariable String fullName) throws IllegalArgumentException, NotFoundException {
        String[] names = fullName.split("-");
        if(names.length > 2)
            throw new InvalidArgumentException("Syntax for full name is wrong!");

         librarianService.deleteLibrarianByFullName(names[0], names[1]);
         return ResponseEntity.ok();
    }

    @DeleteMapping("/{email}.e")
    public ResponseEntity.BodyBuilder deleteLibrarianByEmail(@PathVariable String email) throws IllegalArgumentException, NotFoundException {
        librarianService.deleteLibrarianByEmail(email);
        return ResponseEntity.ok();
    }

    @DeleteMapping
    public ResponseEntity.BodyBuilder deleteAllLibrarians() {
        librarianService.deleteAllLibrarians();
        return ResponseEntity.ok();
    }

}
