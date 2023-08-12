package com.sscn.library.service;

import com.sscn.library.entity.Member;
import com.sscn.library.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {
    private MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    public List<Member> getAllMembers(){
        return memberRepository.findAll();
    }

    public void addNewMember(Member member){
        memberRepository.save(member);
    }

    public void deleteMemberById(int id){
        memberRepository.deleteById(id);
    }

    public Optional<Member> getMemberById(int id){
        return memberRepository.findById(id);
    }
}
