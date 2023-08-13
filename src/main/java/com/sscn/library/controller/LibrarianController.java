package com.sscn.library.controller;

import com.sscn.library.entity.BookReturns;
import com.sscn.library.entity.Librarian;
import com.sscn.library.exception.InvalidArgumentException;
import com.sscn.library.service.LibrarianService;
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
    public List<Librarian> getAllLibrarians() {
        return librarianService.getAllLibrarians();
    }

    @GetMapping("/{id}")
    public Librarian getLibrarianById(@PathVariable Integer id) {
        return librarianService.getLibrarianById(id);
    }

    @GetMapping("/{lastName}.l")
    public List<Librarian> getLibrariansByLastName(@PathVariable String lastName) {
        return librarianService.getLibrariansByLastName(lastName);
    }

    @GetMapping("/{fullName}.fl")
    public List<Librarian> getLibrariansByFullName(@PathVariable String fullName) {
        String[] names = fullName.split("-");
        if(names.length > 2)
            throw new InvalidArgumentException("Syntax for full name is wrong!");

        return librarianService.getLibrariansByFullName(names[0], names[1]);
    }

    @GetMapping("/{email}.e")
    public Librarian getLibrarianByEmail(@PathVariable String email) {
        return getLibrarianByEmail(email);
    }

    @PostMapping
    public List<Librarian> addLibrarians(@RequestBody List<Librarian> librarians) {
        return addLibrarians(librarians);
    }

    @PutMapping("/{librarianId}")
    public Librarian updateLibrarian(@RequestBody Librarian newLibrarian, @PathVariable Integer librarianId) {
        return updateLibrarian(newLibrarian, librarianId);
    }

    @DeleteMapping("/{id}")
    public void deleteLibrarianById(@PathVariable Integer id) {
        librarianService.deleteLibrarianById(id);
    }

    @DeleteMapping("/{lastName}.l")
    public void deleteLibrariansByLastName(@PathVariable String lastName) {
        librarianService.deleteLibrarianByLastName(lastName);
    }

    @DeleteMapping("/{fullName}.fl")
    public void deleteLibrariansByFullName(@PathVariable String fullName) {
        String[] names = fullName.split("-");
        if(names.length > 2)
            throw new InvalidArgumentException("Syntax for full name is wrong!");

        librarianService.deleteLibrarianByFullName(names[0], names[1]);
    }

    @DeleteMapping("/{email}.e")
    public void deleteLibrarianByEmail(@PathVariable String email) {
        librarianService.deleteLibrarianByEmail(email);
    }

    @DeleteMapping
    public void deleteAllLibrarians() {
        librarianService.deleteAllLibrarians();
    }

}
