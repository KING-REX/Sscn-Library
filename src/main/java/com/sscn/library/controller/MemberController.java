package com.sscn.library.controller;

import com.sscn.library.entity.Member;
import com.sscn.library.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/member")
public class MemberController {


    private final MemberService memberService;

    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }


//    @PostMapping("members")
//    public ResponseEntity<Member> createMember(@Valid @RequestBody Member member){
//        Member memberToAdd = memberService.addNewMember(member);
//        return new ResponseEntity<Member>(memberToAdd, HttpStatus.CREATED);
//    }

    @PostMapping("create-member")
    public ResponseEntity<?> createMember(@RequestHeader(value = "apikey", required = false) String apiKey, @Valid @RequestBody Member member){
        Member memberToAdd = memberService.addNewMember(member);
        return new ResponseEntity<Member>(memberToAdd, HttpStatus.CREATED);
    }
//
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationErrors(MethodArgumentNotValidException exception){
        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }

//    @PostMapping("/members")
//    public List<Member> addMembers(@RequestBody List<Member> members){
//        return memberService.addMembers(members);
//    }
}
