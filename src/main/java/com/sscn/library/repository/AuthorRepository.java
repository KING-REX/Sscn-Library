package com.sscn.library.repository;

import com.sscn.library.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
    boolean existsByFirstName(Integer id);
    boolean existsByLastName(Integer id);
    Optional<Author> findByLastName(String firstName);




}
