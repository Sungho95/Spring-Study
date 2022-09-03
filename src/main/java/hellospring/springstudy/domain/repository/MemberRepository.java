package hellospring.springstudy.domain.repository;

import hellospring.springstudy.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member); // 저장소에 저장
    // 저장소에 저장된 Id나 Name을 findById, findByName으로 찾아올 수 있음
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    // findAll을 하면 저장된 모든 정보를 리스트로 받아옴
    List<Member> findAll();
}

// 리포지토리 작성 후 구현체를 만들어야 한다.

