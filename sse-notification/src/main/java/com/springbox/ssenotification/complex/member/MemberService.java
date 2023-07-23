package com.springbox.ssenotification.complex.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long save(MemberSaveRequest memberSaveRequest) {
        Member member = memberRepository.save(new Member(memberSaveRequest.getName()));
        return member.getId();
    }
}
