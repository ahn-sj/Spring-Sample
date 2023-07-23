package com.springbox.ssenotification.complex.review;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/comments/sender/{senderId}/receiver/{receiverId}")
    public void saveReview(@PathVariable Long senderId,
                       @PathVariable Long receiverId,
                       @RequestBody ReviewSaveRequest reviewSaveRequest) {
        reviewService.saveReview(senderId, receiverId, reviewSaveRequest);
    }

}
