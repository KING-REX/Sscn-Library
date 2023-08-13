package com.sscn.library.controller;

import com.sscn.library.entity.Librarian;
import com.sscn.library.service.LibrarianService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/librarian")
public class LibrarianController {

    private final LibrarianService librarianService;

    public LibrarianController(LibrarianService librarianService){
        this.librarianService = librarianService;
    }

//    @PostMapping("/login")
//    public Optional<Librarian> librarianLogin(Librarian librarian){
//        return librarianService.getLibrarianByEmail();
//    }



}
