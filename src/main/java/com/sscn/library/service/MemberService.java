package com.sscn.library.service;

import com.sscn.library.entity.Member;
import com.sscn.library.exception.DuplicateValueException;
import com.sscn.library.exception.NotFoundException;
import com.sscn.library.repository.MemberRepository;
import org.springframework.stereotype.Service;

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

    public void addMember(Member member)  {
//        Member savedMember = memberRepository.findByEmail(member.getEmail()).orElseThrow(()-> new DuplicateValue("\"Member with email \" + savedMember.getEmail() + \"already exists\""));
        Member savedMember = memberRepository.findByEmail(member.getEmail());
        if(memberRepository.existsByEmail(member.getEmail())){
            memberRepository.save(member);
        }
        else{
            throw new DuplicateValueException("Member already exists");
        }
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

    public Member getMemberByEmail(String email){
        Member member = memberRepository.findByEmail(email);
        if(member != null){
            return member;
        }
        else{
            throw new NotFoundException("Email Not Found: " + email);
        }
    }

    public Member getMemberByLastName(String lastName){
        return (Member) memberRepository.findAllByLastName(lastName).orElseThrow(()-> new NotFoundException("Member is not found" + lastName));
    }

    public List<Member> getMembersByLastName(String lastName){
        return memberRepository.findAllByLastName(lastName).orElseThrow(()-> new NotFoundException("Not found"));
    }

    public List<Member> getMembersByFullName(String firstName, String lastName){
        List<Member> revMemberList = memberRepository.findAllByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(()-> new NotFoundException("No Member Found with this name"));
        List<Member> memberList = memberRepository.findAllByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(()-> new NotFoundException("No member found"));

        if(!memberList.isEmpty()){
            return memberList;
        }
        if(!revMemberList.isEmpty()){
            return revMemberList;
        }

        return memberList;
    }
    public void deleteAllMembers(){
        memberRepository.deleteAll();
    }

    public void removeMember(Member member){
        memberRepository.delete(member);
    }

    public Member updateMember(Member newMember, int memberId){
        Member oldMember = getMemberById(memberId);

        System.out.println(oldMember);

        if(newMember.getFirstName() != null && !newMember.getFirstName().isEmpty()){
            oldMember.setFirstName(newMember.getFirstName());
        }

        if(newMember.getLastName() != null && !newMember.getLastName().isEmpty()){
            oldMember.setLastName(newMember.getLastName());
        }

        if(newMember.getEmail() != null && !newMember.getEmail().isEmpty()){
            oldMember.setEmail(newMember.getEmail());
        }

        return memberRepository.save(oldMember);
    }



    public void deleteMemberByEmail(String email){
        removeMember(getMemberByEmail(email));
    }

    public void deleteMemberByLastName(String lastName){
        removeMember(getMemberByLastName(lastName));
    }

    public void deleteMembersByLastName(String lastName){
        getMembersByLastName(lastName).forEach(this::removeMember);
    }

    public void deleteMembersByFullName(String firstName, String lastName){
        getMembersByFullName(firstName, lastName).forEach(this::removeMember);
    }



}
