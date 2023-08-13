package com.sscn.library.service;

import com.sscn.library.entity.Member;
import com.sscn.library.exception.NotFoundException;
import com.sscn.library.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    public List<Member> getAllMembers(){
        return memberRepository.findAll();
    }

    public Member addMember(Member member)  {
        Member savedMember = memberRepository.findByEmail(member.getEmail()).orElseThrow(()-> new RuntimeException("\"Member with email \" + savedMember.getEmail() + \"already exists\""));
        return memberRepository.save(member);
    }

    public List<Member> addMembers(List<Member> members){
        members.forEach(this::addMember);
        return members;
    }

    public void deleteMemberById(Integer id){
        memberRepository.deleteById(id);
    }

    public Member getMemberById(Integer id){
        return memberRepository.findById(id).orElseThrow(() -> new NotFoundException("Member is not found".formatted(id)));
    }

    public Optional<Member> getMemberByEmail(String email){
        Optional<Member> member = memberRepository.findByEmail(email);
        if(member.isPresent()){
            return member;
        }
        else{
            throw new NotFoundException("Email Not Found: " + email);
        }
    }
}
