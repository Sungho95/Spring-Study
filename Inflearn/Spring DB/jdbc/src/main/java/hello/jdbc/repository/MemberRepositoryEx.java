package hello.jdbc.repository;

import hello.jdbc.domain.Member;

import java.sql.SQLException;

// 체크 예외는 인터페이스에서도 throws를 던져야 함
// 특정 기술에 종속되며, 기술 변경시 인터페이스도 변경해야 함
public interface MemberRepositoryEx {
    Member save(Member member) throws Exception;
    Member findById(String memberId) throws SQLException;
    void update(String memberId, int money) throws SQLException;
    void delete(String memberId) throws SQLException;
}