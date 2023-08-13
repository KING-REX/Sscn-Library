package com.sscn.library.service;

import com.sscn.library.entity.Member;
import com.sscn.library.exception.DuplicateValueException;
import com.sscn.library.exception.NotFoundException;
import com.sscn.library.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    public List<Member> getAllMembers(){
        return memberRepository.findAll();
    }

    public Member addNewMember(Member member)  {
        Member savedMember = memberRepository.findByMail(member.getEmail());
        if(savedMember != null){
            throw new RuntimeException("Member with email " + savedMember.getEmail() + "already exists");
        }
        return memberRepository.save(member);
    }

    public List<Member> addMembers(List<Member> members){
        members.forEach(member -> {
            this.addNewMember(member);
        });
        return members;
    }

    public void deleteMemberById(Integer id){
        memberRepository.deleteById(id);
    }

    public Member getMemberById(Integer id){
        return memberRepository.findById(id).orElseThrow(() -> new NotFoundException("Member is not found".formatted(id)));
    }

    public Member getMemberByEmail(String email){
        Member member = memberRepository.findByMail(email);
        if(member != null){
            return member;
        }
        else{
            throw new NotFoundException("Email Not Found: " + email);
        }
    }
}
