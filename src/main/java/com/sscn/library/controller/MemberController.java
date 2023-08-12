package com.sscn.library.controller;

import com.sscn.library.entity.Member;
import com.sscn.library.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
public class MemberController {

    @Autowired
    private MemberService memberService;


//    @PostMapping("members")
//    public ResponseEntity<Member> createMember(@Valid @RequestBody Member member){
//        Member memberToAdd = memberService.addNewMember(member);
//        return new ResponseEntity<Member>(memberToAdd, HttpStatus.CREATED);
//    }

    @PostMapping("members")
    public ResponseEntity<?> createMember(@RequestHeader(value = "apikey", required = false) String apiKey, @Valid @RequestBody Member member){
        Member memberToAdd = memberService.addNewMember(member);
        return new ResponseEntity<Member>(memberToAdd, HttpStatus.CREATED);
    }
}
