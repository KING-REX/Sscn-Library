package com.sscn.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<AuthorRepository, Integer> {
    boolean existsAuthorById(int id);

}
