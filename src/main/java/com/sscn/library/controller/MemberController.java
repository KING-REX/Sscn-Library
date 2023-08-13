package com.sscn.library.controller;

import com.sscn.library.entity.Member;
import com.sscn.library.exception.DuplicateValueException;
import com.sscn.library.exception.NotFoundException;
import com.sscn.library.service.MemberService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class MemberController {


    @Autowired
    private final MemberService memberService;

    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }


    @PostMapping("/member")
    public ResponseEntity<Member> createMember(@Valid @RequestBody Member member) throws RuntimeException {
        Member memberToAdd = memberService.addNewMember(member);
        return new ResponseEntity<>(memberToAdd, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable int id) throws NotFoundException{
        return ResponseEntity.ok(memberService.getMemberById(id));
    }
    @GetMapping("/{email}")
    public ResponseEntity<Member> getMemberByEmail(@PathVariable String email) throws NotFoundException{
        return ResponseEntity.ok(memberService.getMemberByEmail(email));
    }
}
