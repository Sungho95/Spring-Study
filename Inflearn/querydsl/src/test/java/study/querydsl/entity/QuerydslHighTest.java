package study.querydsl.entity;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.dto.MemberDto;
import study.querydsl.dto.UserDto;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import java.util.List;

import static study.querydsl.entity.QMember.member;

@SpringBootTest
@Transactional
public class QuerydslHighTest {

    @Autowired
    EntityManager em;

    @PersistenceUnit
    EntityManagerFactory emf;

    JPAQueryFactory queryFactory;

    @BeforeEach
    public void before() {
        queryFactory = new JPAQueryFactory(em);

        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);

        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);
    }

    /**
     * 프로젝션과 결과 반환 - 기본
     */
    @Test
    public void simpleProjection() throws Exception {

        List<String> result = queryFactory
                .select(member.username)
                .from(member)
                .fetch();

        for (String s : result) {
            System.out.println("s = " + s);
        }
    }

    /**
     * Tuple
     * 프로젝션 대상이 둘 이상이면 Tuple이나 DTO로 조회
     * 하지만, 컨트롤러나 서비스에 던져주는 값은 Tuple이 아닌 DTO로 조회하는 것을 권장함.
     * Tuple은 Repository 내에서만 사용하는 것을 권장
     */
    @Test
    public void tupleProjection() throws Exception {

        List<Tuple> result = queryFactory
                .select(member.username, member.age)
                .from(member)
                .fetch();

        for (Tuple tuple : result) {
            String username = tuple.get(member.username);
            Integer age = tuple.get(member.age);
            System.out.println("username = " + username);
            System.out.println("age = " + age);
        }
    }

    /**
     * 중요!!
     * 프로젝션과 결과 반환 - DTO 조회
     * - 순수 JPA에서 DTO를 조회할 때는 new 명령어를 사용해야 한다.
     * - DTO 패키지 이름을 다 적어줘야 하는 단점이 있음
     * - 생성자 방식만 지원
     */
    @Test
    public void findDtoByJPQL() throws Exception {

        String jpql = "select new study.querydsl.dto.MemberDto(m.username, m.age) from Member m";

        List<MemberDto> result = em.createQuery(jpql, MemberDto.class)
                .getResultList();

        for (MemberDto memberDto : result) {
            System.out.println("memberDto = " + memberDto);
        }

    }

    /**
     * Querydsl 빈 생성(Bean population)
     * - 결과를 DTO 반환할 때 사용
     * - 프로퍼티 접근 가능
     * - 필드 직접 접근 가능
     * - 생성자 사용 가능
     */

    /**
     * 프로퍼티 접근 방법
     * - Setter 필요
     */
    @Test
    public void findDtoBySetter() throws Exception {

        List<MemberDto> result = queryFactory
                .select(Projections.bean(MemberDto.class,
                        member.username,
                        member.age))
                .from(member)
                .fetch();

        for (MemberDto memberDto : result) {
            System.out.println("memberDto = " + memberDto);
        }

    }

    /**
     * 필드 직접 접근 방법
     * - Setter 없이 필드에 바로 접근
     */
    @Test
    public void findDtoByField() throws Exception {

        List<MemberDto> result = queryFactory
                .select(Projections.fields(MemberDto.class,
                        member.username,
                        member.age))
                .from(member)
                .fetch();

        for (MemberDto memberDto : result) {
            System.out.println("memberDto = " + memberDto);
        }

    }

    /**
     * 생성자 방식
     * - 생성자 필요
     */
    @Test
    public void findDtoByConstructor() throws Exception {

        List<MemberDto> result = queryFactory
                .select(Projections.constructor(MemberDto.class,
                        member.username,
                        member.age))
                .from(member)
                .fetch();

        for (MemberDto memberDto : result) {
            System.out.println("memberDto = " + memberDto);
        }
    }

    /**
     * UserDto에는 필드가 username이 아닌 name으로 되어 있음
     * - member.username과 매칭이 안되기 때문에 null을 반환하게 됨.
     * - as("name")으로 맞출 수 있음 - 필드에 적용 가능
     *
     * ExpressionUtils.as(source, alias)
     * 필드나 서브 쿼리에 별칭(alias)을 적용할 수 있음
     */
    @Test
    public void findUserDto() throws Exception {

        QMember memberSub = new QMember("memberSub");

        List<UserDto> result = queryFactory
                .select(Projections.fields(UserDto.class,
                        member.username.as("name"),

                        ExpressionUtils.as(JPAExpressions
                                .select(memberSub.age.max())
                                .from(memberSub), "age")
                        ))
//                        member.age))
                .from(member)
                .fetch();

        for (UserDto userDto : result) {
            System.out.println("userDto = " + userDto);
        }
    }

}
