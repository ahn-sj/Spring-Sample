package springbox.slackbot.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springbox.slackbot.domain.Member;
import springbox.slackbot.dto.CheckInResponse;
import springbox.slackbot.dto.CheckOutResponse;
import springbox.slackbot.repository.MemberRepository;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class CheckInOutController {

    private final MemberRepository memberRepository;

    @PostMapping("/check-in")
    public CheckInResponse checkIn(@ModelAttribute CheckRequest checkInRequest) {
        String username = checkInRequest.getUser_name();

        Member member = memberRepository.save(new Member(username));

        log.info("username = {}, checkInTime = {}", member.getUsername(), member.getCheckInTime());

        return new CheckInResponse(member.getCheckInTime(), member.getUsername());
    }

    @PostMapping("/check-out")
    public CheckOutResponse checkOut(@ModelAttribute CheckRequest checkInRequest) {
        Member member = findMember(checkInRequest);
        member.checkOut();
        memberRepository.save(member);

        return new CheckOutResponse(member.getCheckOutTime(), member.getWorkTime(), member.getUsername());
    }

    private Member findMember(CheckRequest checkInRequest) {
        return memberRepository.findByUsername(checkInRequest.getUser_name()).orElseThrow(() -> {
            throw new IllegalStateException("체크인이 되지 않았습니다.");
        });
    }

    @Data
    public class CheckRequest {
        private String user_name;
    }
}
