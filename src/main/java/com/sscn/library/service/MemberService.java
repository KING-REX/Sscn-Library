package com.sscn.library.service;

import com.sscn.library.entity.Member;
import com.sscn.library.exception.DuplicateValueException;
import com.sscn.library.exception.InvalidArgumentException;
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

    public Member getMemberById(Integer id){
        return memberRepository.findById(id).orElseThrow(() -> new NotFoundException("Member %s is not found".formatted(id)));
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

    public List<Member> getMembersByLastName(String lastName){
        return memberRepository.findAllByLastName(lastName).orElseThrow(()-> new NotFoundException("Member %s not found".formatted(lastName)));
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

    public Member addMember(Member member)  {
        if(member.getId() != null && memberRepository.existsById(member.getId()))
            throw new DuplicateValueException("Librarian %s already exists.".formatted(member.getId()));
        else if(member.getId() != null)
            throw new InvalidArgumentException("Member Id is auto-generated. Don't give it a value!");

        if(member.getFirstName() == null)
            throw new InvalidArgumentException("First name cannot be null!");
        else if(member.getFirstName().isEmpty())
            throw new InvalidArgumentException("First name cannot be empty!");

        if(member.getLastName() == null)
            throw new InvalidArgumentException("Last name cannot be null!");
        else if(member.getLastName().isEmpty())
            throw new InvalidArgumentException("Last name cannot be empty!");

        if(member.getEmail() == null)
            throw new InvalidArgumentException("Email cannot be null!");
        else if(member.getEmail().isEmpty())
            throw new InvalidArgumentException("Email cannot be empty!");
        else if(memberRepository.existsByEmail(member.getEmail()))
            throw new InvalidArgumentException("Email %s already exists!".formatted(member.getEmail()));

        return memberRepository.save(member);
    }

    public List<Member> addMembers(List<Member> members){
        members.forEach(this::addMember);
        return members;
    }

    public Member updateMember(Member newMember, int memberId){
        Member oldMember = getMemberById(memberId);

//        System.out.println(oldMember);

        if(newMember.getFirstName() != null){
            if(newMember.getFirstName().isEmpty())
                throw new InvalidArgumentException("First name cannot be empty!");
            oldMember.setFirstName(newMember.getFirstName());
        }

        if(newMember.getLastName() != null){
            if(newMember.getLastName().isEmpty())
                throw new InvalidArgumentException("Last name cannot be empty!");
            oldMember.setLastName(newMember.getLastName());
        }

        if(newMember.getEmail() != null){
            if(newMember.getEmail().isEmpty())
                throw new InvalidArgumentException("Email cannot be empty!");
            oldMember.setEmail(newMember.getEmail());
        }

        return memberRepository.save(oldMember);
    }

    public void removeMember(Member member){
        memberRepository.delete(member);
    }

    public void deleteMemberById(Integer id){
        memberRepository.deleteById(id);
    }



    public void deleteMemberByEmail(String email){
        removeMember(getMemberByEmail(email));
    }

    public void deleteMembersByLastName(String lastName){
        getMembersByLastName(lastName).forEach(this::removeMember);
    }

    public void deleteMembersByFullName(String firstName, String lastName){
        getMembersByFullName(firstName, lastName).forEach(this::removeMember);
    }

    public void deleteAllMembers(){
        memberRepository.deleteAll();
    }
}
