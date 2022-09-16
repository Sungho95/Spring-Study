package hellospring.springstudy.domain.repository;

import hellospring.springstudy.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository // 리포지토리임을 알리는 어노테이션
public class MemoryMemberRepository implements MemberRepository {

    // 다음과 같이 할 경우 동시성 문제를 고려해야 하나, 간단하게 예제를 진행
    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
