package com.sscn.library.service;

import com.sscn.library.entity.Author;
import com.sscn.library.entity.Librarian;
import com.sscn.library.exception.DuplicateValueException;
import com.sscn.library.exception.InvalidArgumentException;
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
        else if(librarian.getId() != null)
            throw new InvalidArgumentException("Librarian Id is auto-generated. Don't give it a value!");

        if(librarian.getFirstName() == null)
            throw new InvalidArgumentException("First name cannot be null!");
        else if(librarian.getFirstName().isEmpty())
            throw new InvalidArgumentException("First name cannot be empty!");


        if(librarian.getLastName() == null)
            throw new InvalidArgumentException("Last name cannot be null!");
        else if(librarian.getLastName().isEmpty())
            throw new InvalidArgumentException("Last name cannot be empty!");

        if(librarian.getPassword() == null)
            throw new InvalidArgumentException("Password cannot be null!");
        else if(librarian.getPassword().isEmpty())
            throw new InvalidArgumentException("Password cannot be empty!");

        if(librarian.getEmail() != null) {
            if(librarianRepository.existsByEmail(librarian.getEmail()))
                throw new DuplicateValueException("Librarian with email %s already exists.".formatted(librarian.getEmail()));
            else if(librarian.getEmail().isEmpty())
                throw new InvalidArgumentException("Email cannot be empty!");
        }
        else
            throw new InvalidArgumentException("Email cannot be null!");


        return librarianRepository.save(librarian);
    }

    public List<Librarian> addLibrarians(List<Librarian> librarians) {
        librarians.forEach(this::addLibrarian);

        return librarians;
    }

    public Librarian updateLibrarian(Librarian newLibrarian, Integer id) {
        Librarian oldLibrarian = getLibrarianById(id);

        if(newLibrarian.getFirstName() != null) {
            if(newLibrarian.getFirstName().isEmpty())
                throw new InvalidArgumentException("First name cannot be empty!");
            oldLibrarian.setFirstName(newLibrarian.getFirstName());
        }

        if(newLibrarian.getLastName() != null) {
            if(newLibrarian.getLastName().isEmpty())
                throw new InvalidArgumentException("Last name cannot be empty!");
            oldLibrarian.setLastName(newLibrarian.getLastName());
        }

        if(newLibrarian.getEmail() != null) {
            if(newLibrarian.getEmail().isEmpty())
                throw new InvalidArgumentException("Email cannot be empty!");
            else if(librarianRepository.existsByEmail(newLibrarian.getEmail()))
                throw new DuplicateValueException("Librarian with email %s already exists.".formatted(newLibrarian.getEmail()));
            oldLibrarian.setEmail(newLibrarian.getEmail());
        }

        if(newLibrarian.getPassword() != null && !newLibrarian.getPassword().isEmpty()) {

            String password = newLibrarian.getPassword();

            //TODO: Hash password before setting the newPassword below.
            oldLibrarian.setPassword(password);
        }

        return librarianRepository.save(oldLibrarian);
    }

    public void deleteLibrarian(Librarian librarian) {
        librarianRepository.delete(librarian);
    }

    public void deleteLibrarianById(Integer id) {
        deleteLibrarian(getLibrarianById(id));
    }

    public void deleteLibrarianByEmail(String email) {
        deleteLibrarian(getLibrarianByEmail(email));
    }

    public void deleteLibrarianByLastName(String lastName) {
        getLibrariansByLastName(lastName).forEach(this::deleteLibrarian);
    }

    public void deleteLibrarianByFullName(String firstName, String lastName) {
        getLibrariansByFullName(firstName, lastName).forEach(this::deleteLibrarian);
    }

    public void deleteAllLibrarians() {
        librarianRepository.deleteAll();
    }
}
