package com.sscn.library.repository;

import com.sscn.library.entity.Author;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

//    boolean existsByFirstName(String firstName);
    boolean existsByLastName(String lastName);

//    Optional<Author> findByLastName(String lastName);
    Optional<List<Author>> findAllByLastName(String lastName);

}
