package com.sscn.library.service;

import com.sscn.library.entity.Librarian;
import com.sscn.library.repository.LibrarianRepository;

import java.util.List;

public class LibrarianService {

    private LibrarianRepository librarianRepository;

    public LibrarianService(LibrarianRepository librarianRepository){
        this.librarianRepository = librarianRepository;
    }

    public List<Librarian> getALlLibrarians(){

    }
}
