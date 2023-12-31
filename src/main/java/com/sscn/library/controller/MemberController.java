package com.sscn.library.controller;

import com.sscn.library.entity.Member;
import com.sscn.library.exception.DuplicateValueException;
import com.sscn.library.exception.InvalidArgumentException;
import com.sscn.library.exception.NotFoundException;
import com.sscn.library.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/members")
public class MemberController {


    @Autowired
    private final MemberService memberService;

    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    @GetMapping
    public ResponseEntity<List<Member>> getAllMembers() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable int id) throws NotFoundException{
        return ResponseEntity.ok(memberService.getMemberById(id));
    }
    @GetMapping("/{email}.e")
    public ResponseEntity<Member> getMemberByEmail(@PathVariable String email) throws NotFoundException{
        return ResponseEntity.ok(memberService.getMemberByEmail(email));
    }

    @GetMapping("/{lastName}.l")
    public ResponseEntity<List<Member>> getMembersByLastName(@PathVariable String lastName) throws NotFoundException {
        return ResponseEntity.ok(memberService.getMembersByLastName(lastName));
    }

    @GetMapping("/{fullName}.fl")
    public ResponseEntity<List<Member>> getMembersByFullName(@PathVariable String fullName) throws NotFoundException {
        String[] names = fullName.split("-");
        if(names.length > 2)
            throw new InvalidArgumentException("Syntax for full name is wrong!");

        return ResponseEntity.ok(memberService.getMembersByFullName(names[0], names[1]));
    }

    @PostMapping
    public ResponseEntity<List<Member>> addMembers(@Valid @RequestBody List<Member> members) throws DuplicateValueException, IllegalStateException, IllegalArgumentException {
        List<Member> membersToAdd = memberService.addMembers(members);
        return new ResponseEntity<>(membersToAdd, HttpStatus.CREATED);
    }

    @PutMapping("/{memberId}")
    public ResponseEntity<Member> updateMember(@Valid @RequestBody Member newMember, @PathVariable Integer memberId) throws NotFoundException, IllegalArgumentException {
        Member member =  memberService.updateMember(newMember, memberId);
        return ResponseEntity.ok(member);
    }

    @DeleteMapping
    public ResponseEntity.BodyBuilder deleteAllMembers() {
        memberService.deleteAllMembers();
        return ResponseEntity.ok();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity.BodyBuilder deleteMemberById(@PathVariable Integer id) throws IllegalArgumentException, NotFoundException {
        memberService.deleteMemberById(id);
        return ResponseEntity.ok();
    }

    @DeleteMapping("/{email}.e")
    public ResponseEntity.BodyBuilder deleteMemberByEmail(@PathVariable String email) throws IllegalArgumentException, NotFoundException {
        memberService.deleteMemberByEmail(email);
        return ResponseEntity.ok();
    }

    @DeleteMapping("/{lastName}.l")
    public ResponseEntity.BodyBuilder deleteMembersByLastName(@PathVariable String lastName) throws IllegalArgumentException, NotFoundException {
        memberService.deleteMembersByLastName(lastName);
        return ResponseEntity.ok();
    }

    @DeleteMapping("/{fullName}.fl")
    public ResponseEntity.BodyBuilder deleteMembersByFullName(@PathVariable String fullName) throws IllegalArgumentException, NotFoundException {
        String[] names = fullName.split("-");
        if(names.length > 2){
            throw new InvalidArgumentException("Full Name is Wrong");
        }
        memberService.deleteMembersByFullName(names[0], names[1]);
        return ResponseEntity.ok();
    }
}
