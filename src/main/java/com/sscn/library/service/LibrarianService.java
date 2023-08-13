package com.sscn.library.service;

import com.sscn.library.entity.Author;
import com.sscn.library.entity.Librarian;
import com.sscn.library.exception.DuplicateValueException;
import com.sscn.library.exception.NotFoundException;
import com.sscn.library.repository.LibrarianRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LibrarianService {

    private final LibrarianRepository librarianRepository;

    public LibrarianService(LibrarianRepository librarianRepository){
        this.librarianRepository = librarianRepository;
    }

    public List<Librarian> getAllLibrarians() {
        return librarianRepository.findAll();
    }

    public Librarian getLibrarianById(Integer id) {
        return librarianRepository.findById(id).orElseThrow(() -> new NotFoundException("Librarian %s not found".formatted(id)));
    }

    public List<Librarian> getLibrariansByLastName(String lastName) {
        return librarianRepository.findAllByLastName(lastName).orElseThrow(() -> new NotFoundException("Librarian %s not found".formatted(lastName)));
    }

    public List<Librarian> getLibrariansByFullName(String firstName, String lastName) {
        List<Librarian> revLibrarianList = librarianRepository
                .findAllByLastNameAndFirstName(firstName, lastName)
                .orElseThrow(() -> new NotFoundException("No librarian with the name %s %s exists.".formatted(firstName, lastName)));
        List<Librarian> librarianList = librarianRepository
                .findAllByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(() -> new NotFoundException("No librarian with the name %s %s exists.".formatted(firstName, lastName)));

        if(!librarianList.isEmpty())
            return librarianList;
        if(!revLibrarianList.isEmpty())
            return revLibrarianList;

        return librarianList;
    }

    public Librarian getLibrarianByEmail(String email) {
        return librarianRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("No librarian with email %s exists".formatted(email)));
    }

    public Librarian addLibrarian(Librarian librarian) {
        if(librarian.getId() != null && librarianRepository.existsById(librarian.getId()))
            throw new DuplicateValueException("Librarian %s already exists.".formatted(librarian.getId()));

        if(librarian.getEmail() != null && librarianRepository.existsByEmail(librarian.getEmail()))
            throw new DuplicateValueException("Librarian with email %s already exists.".formatted(librarian.getEmail()));


        return librarianRepository.save(librarian);
    }

    public List<Librarian> addLibrarians(List<Librarian> librarians) {
        librarians.forEach(this::addLibrarian);

        return librarians;
    }

    public Librarian updateLibrarian(Librarian newLibrarian, Integer id) {
        Librarian oldLibrarian = getLibrarianById(id);

        if(newLibrarian.getFirstName() != null && !newLibrarian.getFirstName().isEmpty())
            oldLibrarian.setFirstName(newLibrarian.getFirstName());
        if(newLibrarian.getLastName() != null && !newLibrarian.getLastName().isEmpty())
            oldLibrarian.setLastName(newLibrarian.getLastName());
        if(newLibrarian.getEmail() != null && !newLibrarian.getEmail().isEmpty() && !librarianRepository.existsByEmail(newLibrarian.getEmail()))
            oldLibrarian.setEmail(newLibrarian.getEmail());

        if(newLibrarian.getPassword() != null && !newLibrarian.getPassword().isEmpty()) {

            String password = newLibrarian.getPassword();

            //TODO: Hash password before setting the newPassword below.
            oldLibrarian.setPassword(password);
        }

        return librarianRepository.save(oldLibrarian);
    }
}
