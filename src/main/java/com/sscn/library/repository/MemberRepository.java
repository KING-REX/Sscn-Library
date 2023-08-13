package com.sscn.library.repository;

import com.sscn.library.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

    boolean existsByLastName(String lastName);
    boolean existsByEmail(String email);

    Optional<Member> findByEmail(String email);
//    Optional<Member> findByLastName(String lastName);
    Optional<List<Member>> findAllByLastName(String email);

    public Member findByMail(String email);

}
