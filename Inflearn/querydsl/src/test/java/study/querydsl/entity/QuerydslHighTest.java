package study.querydsl.entity;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.dto.MemberDto;
import study.querydsl.dto.QMemberDto;
import study.querydsl.dto.UserDto;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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

    /**
     * @QueruProjection
     * - DTO에 @QueryProjection 어노테이션을 붙여서 사용
     * 장점
     * - 컴파일 오류를 발생하여 실행 전에 에러를 잡을 수 있다.
     * 단점
     * - Q 파일을 생성해야 한다.
     * - QueryProjection 어노테이션으로 인한 의존성 문제가 발생함.
     * - 해당 DTO는 querydsl에 의존하게 됨. (설계 관점에서 좋지 못한 상태)
     */
    @Test
    public void findDtoByQueryProjection() throws Exception {

        List<MemberDto> result = queryFactory
                .select(new QMemberDto(member.username, member.age))
                .from(member)
                .fetch();

        for (MemberDto memberDto : result) {
            System.out.println("memberDto = " + memberDto);
        }
    }

    /**
     * 동적 쿼리를 해결하는 두 가지 방식
     * - BooleanBuilder 사용
     * - Where 다중 파라미터 사용
     */
    @Test
    public void dynamicQuery_BooleanBuilder() throws Exception {

        String usernameParam = "member1";
        Integer ageParam = null; // 10

        List<Member> result = searchMember1(usernameParam, ageParam);
        assertThat(result.size()).isEqualTo(1);
    }

    private List<Member> searchMember1(String usernameCond, Integer ageCond) {

        // BooleanBuilder builder = new BooleanBuilder(member.username.eq(usernameCond));
        BooleanBuilder builder = new BooleanBuilder();

        if (usernameCond != null) {
            builder.and(member.username.eq(usernameCond));
        }

        if (ageCond != null) {
            builder.and(member.age.eq(ageCond));
        }


        return queryFactory
                .selectFrom(member)
                .where(builder) // builder.and() 가능
                .fetch();
    }

    @Test
    public void dynamicQuery_WhereParam() throws Exception {
        String usernameParam = "member1";
        Integer ageParam = null; // 10

        List<Member> result = searchMember2(usernameParam, ageParam);
        assertThat(result.size()).isEqualTo(1);
    }

    private List<Member> searchMember2(String usernameCond, Integer ageCond) {
        return queryFactory
                .selectFrom(member)
                .where(usernameEq(usernameCond), ageEq(ageCond))
//                .where(allEq(usernameCond, ageCond))
                .fetch();
    }

    private BooleanExpression usernameEq(String usernameCond) {

        return usernameCond != null ? member.username.eq(usernameCond) : null;
//        if (usernameCond == null) {
//            return null;
//        }
//
//        return member.username.eq(usernameCond);
    }

    private BooleanExpression ageEq(Integer ageCond) {
        return ageCond != null ? member.age.eq(ageCond) : null;
    }

    // 광고 상태 isValid, 광고 날짜 isServiceable ... 등 여러 조건을 한 번에 사용할 수 있다.
    // 재사용 가능
    private BooleanExpression allEq(String usernameCond, Integer ageCond) {
        return usernameEq(usernameCond).and(ageEq(ageCond));
    }

    /**
     * 벌크 연산
     * 주의할 점
     * - 벌크 연산 수행 후 영속성 컨텍스트 내용과 DB의 내용이 다름
     */
    @Test
    public void bulkUpdate() throws Exception {

        // 실행 전
        // member1 = 10 -> member1
        // member2 = 20 -> member2
        // member3 = 30 -> member3
        // member4 = 40 -> member4

        long count = queryFactory
                .update(member)
                .set(member.username, "비회원")
                .where(member.age.lt(28))
                .execute();

        // DB                             영속성 컨텍스트
        // member1 = 10 -> 비회원          member1 = 10 -> member1
        // member2 = 20 -> 비회원          member2 = 20 -> member2
        // member3 = 30 -> member3        member3 = 30 -> member3
        // member4 = 40 -> member4        member4 = 40 -> member4

//        List<Member> result = queryFactory
//                .selectFrom(member)
//                .fetch();
//
//        // member1과 member2의 이름이 변경된 상태로 출력되지 않음
//        for (Member member1 : result) {
//            System.out.println("member1 = " + member1);
//        }

        // 벌크 연산 시에는 항상 flush()와 clear()를 사용하자.
        em.flush();
        em.clear();

        List<Member> result2 = queryFactory
                .selectFrom(member)
                .fetch();

        for (Member member1 : result2) {
            System.out.println("member1 = " + member1);
        }
    }

    @Test
    public void bulkAdd() throws Exception {

        long count = queryFactory
                .update(member)
                .set(member.age, member.age.add(1)) // add(-1)
                .execute();
    }

    @Test
    public void bulkDelete() throws Exception {

        long count = queryFactory
                .delete(member)
                .where(member.age.gt(18))
                .execute();
    }

    @Test
    public void sqlFunction() throws Exception {

        List<String> result = queryFactory
                .select(Expressions.stringTemplate(
                        "function('replace', {0}, {1}, {2})",
                        member.username, "member", "M"))
                .from(member)
                .fetch();

        for (String s : result) {
            System.out.println("s = " + s);
        }
    }

    @Test
    public void sqlFunction2() throws Exception {

        List<String> result = queryFactory
                .select(member.username)
                .from(member)
//                .where(member.username.eq(
//                        Expressions.stringTemplate("function('lower', {0})", member.username)))
                .where(member.username.eq(member.username.lower()))
                .fetch();

        for (String s : result) {
            System.out.println("s = " + s);
        }
    }
}
