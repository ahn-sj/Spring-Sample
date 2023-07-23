package com.springbox.ssenotification.complex.review;

import com.springbox.ssenotification.complex.member.Member;
import com.springbox.ssenotification.complex.member.MemberRepository;
import com.springbox.ssenotification.complex.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final NotificationService notificationService;

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void saveReview(Long senderId, Long receiverId, ReviewSaveRequest reviewSaveRequest) {
        Review review = new Review(reviewSaveRequest.getContent(), senderId, receiverId);
        reviewRepository.save(review);

        Member receiver = memberRepository.findById(receiverId).orElseThrow();

        notificationService.send(receiver, review, review.getContent());
    }
}
