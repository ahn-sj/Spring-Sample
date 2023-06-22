package com.springbox.ssenotification.complex;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final NotificationService notificationService;

    @GetMapping("/comments/{id}")
    public void create(@PathVariable Long id) {
        Review review = new Review("리뷰를 남겼습니다");
        Member member = new Member("회원A");

        System.out.println(member.getName() + " 이 " + review.getContent());

        notificationService.send(Member.builder().id(id).name("target 회원").build(), review, "알림을 보냄");

    }

}
