package com.sscn.library.repository;

import com.sscn.library.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Member, Integer> {

}
