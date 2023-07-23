package com.springbox.ssenotification.complex.member;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/members/save")
    public ResponseEntity<Long> save(@RequestBody MemberSaveRequest memberSaveRequest) {
        return ResponseEntity.ok(memberService.save(memberSaveRequest));
    }
}
