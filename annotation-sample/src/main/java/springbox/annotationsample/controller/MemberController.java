package springbox.annotationsample.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springbox.annotationsample.annotation.AuthorizationHeader;
import springbox.annotationsample.annotation.KoreanNameChecker;
import springbox.annotationsample.annotation.Sorter;
import springbox.annotationsample.domain.Member;
import springbox.annotationsample.dto.MemberRequest;
import springbox.annotationsample.dto.MemberResponse;
import springbox.annotationsample.dto.MemberUpdateRequest;
import springbox.annotationsample.repository.MemberRepository;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class MemberController {

    private final MemberRepository memberRepository;

    public MemberController(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @GetMapping("/members")
    public ResponseEntity<List<MemberResponse>> findAll() {
        List<MemberResponse> memberResponseList = getMemberResponseList();
        return ResponseEntity.ok(memberResponseList);
    }

    private List<MemberResponse> getMemberResponseList() {
        return memberRepository.findAll()
                .stream()
                .map(m -> new MemberResponse(m.getName(), m.getAuthority().getDesc()))
                .collect(Collectors.toList());
    }

    @PostMapping("/members")
    public ResponseEntity<Void> save(@RequestBody final MemberRequest memberRequest) {
        Member member = memberRepository.save(new Member(memberRequest.getName(), memberRequest.getAuthority()));

        return ResponseEntity.created(URI.create("/api/members/" + member.getId())).build();
    }

    @PutMapping("/members/{id}")
    @KoreanNameChecker
    public ResponseEntity<MemberResponse> update(@PathVariable final Long id,
                                                 @RequestBody final MemberUpdateRequest memberRequest) {
        Member member = findMember(id);

        member.updateName(memberRequest.getName());
        memberRepository.save(member);

        return ResponseEntity.ok(new MemberResponse(member.getName(), member.getAuthority().getDesc()));
    }

    private Member findMember(Long id) {
        return memberRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 사용자입니다."));
    }

    @DeleteMapping("/members/{id}")
    @AuthorizationHeader
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        memberRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @Sorter(order = "DESC")
    @GetMapping("/list/desc")
    public List<Integer> findAllDESC() {
        List<Integer> rst = Arrays.asList(100, 1, 50);
        return rst;
    }

    @Sorter(order = "ASC")
    @GetMapping("/list/asc")
    public List<Integer> findAllASC() {
        List<Integer> rst = Arrays.asList(100, 1, 50);
        return rst;
    }
}
