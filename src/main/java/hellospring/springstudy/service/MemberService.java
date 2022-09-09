package hellospring.springstudy.service;

import hellospring.springstudy.domain.Member;
import hellospring.springstudy.domain.repository.MemberRepository;
import hellospring.springstudy.domain.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

// Ctr + shift + t 단축키를 통해 Create Test
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원 가입
     */
    public Long join(Member member) {
        // 중복 회원 처리 로직
//        Optional<Member> result = memberRepository.findByName(member.getName());
//
//        result.ifPresent(m -> {
//            throw new IllegalStateException("이미 존재하는 회원입니다.");
//        });

        // 메서드로 수정 가능
        validateDuplicateMember(member);
        // 회원가입
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 아이디 조회
     */
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
