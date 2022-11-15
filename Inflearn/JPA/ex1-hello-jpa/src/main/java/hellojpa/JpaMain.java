package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
//            Member member = new Member();
//            member.setId(1L);
//            member.setUsername("A");
//            member.setRoleType(RoleType.USER);

            Member member = new Member();
            member.setId(2L);
            member.setUsername("A");
            member.setRoleType(RoleType.ADMIN);

            em.persist(member);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}

        // 등록
//        try {
//            Member member = new Member();
//            member.setId(2L);
//            member.setName("HelloB");
//
//            em.persist(member);
//
//            tx.commit();
//        } catch (Exception e) {
//            tx.rollback();
//        } finally {
//            em.close();
//        }
//        emf.close();

//        // 수정 및 삭제
//        try {
//            Member findMember = em.find(Member.class, 1L);
//            findMember.setName("HelloJPA");
////            em.remove(findMember); // 삭제
//
//            System.out.println("findMember.getId() = " + findMember.getId());
//            System.out.println("findMember.getName() = " + findMember.getName());
//
//            tx.commit();
//        } catch (Exception e) {
//            tx.rollback();
//        } finally {
//            em.close();
//        }
//        emf.close();

        // 나이가 18이상인 회원을 모두 검색하고 싶다면?
        // JPQL 사용해야 한다. JPQL은 엔티티 객체 대상으로 쿼리
        // SELECT, FROM, WHERE, GROUP BY, HAVING, JOIN 지원
//        try {
//            List<Member> result = em.createQuery("select m from Member as m", Member.class)
//                    .setFirstResult(1)
//                    .setMaxResults(10)
//                    .getResultList();
//
//            for (Member member : result) {
//                System.out.println("member.getName() = " + member.getName());
//            }
//
//            tx.commit();
//        } catch (Exception e) {
//            tx.rollback();
//        } finally {
//            em.close();
//        }
//        emf.close();

//        try {
//            // 비영속
//            Member member = new Member();
//            member.setId(101L);
//            member.setName("HelloJPA");
//
//            // 영속
//            System.out.println("===before===");
//            em.persist(member); // 영속성 컨텍스트에 저장, 영속 상태
////            em.detach(member); // 영속성 컨텍스트에서 삭제, 준영속 상태
//            System.out.println("===after===");
//
//            Member findMember = em.find(Member.class, 101L);
//            System.out.println("findMember = " + findMember.getId());
//            System.out.println("findMember.getName() = " + findMember.getName());
//
////            em.remove(member); // 객체를 삭제
//
//            tx.commit();
//        } catch (Exception e) {
//            tx.rollback();
//        } finally {
//            em.close();
//        }
//        emf.close();

//        try {
//            // 쿼리 문을 통해 조회 후 1차 캐시에 저장
//            Member findMember1 = em.find(Member.class, 101L);
//            // 영속성 컨텍스트의 1차 캐시에서 바로 조회
//            Member findMember2 = em.find(Member.class, 101L);
//            // 따라서 쿼리문은 1번만 실행하게 됨.
//
//            // true : 동일성 보장
//            System.out.println("result = " + (findMember1 == findMember2));
//
//            // 커밋 하는 순간에 데이터베이스에 보냄.
//            tx.commit();
//        } catch (Exception e) {
//            tx.rollback();
//        } finally {
//            em.close();
//        }
//        emf.close();

//        try {
//            Member member = em.find(Member.class, 150L);
//            member.setName("ZZZZZ");
//
////            em.persist(member);
//
//            tx.commit();
//        } catch (Exception e) {
//            tx.rollback();
//        } finally {
//            em.close();
//        }
//        emf.close();

//        try {
//            Member member = em.find(Member.class, 150L);
//            member.setName("AAAAA");
//
//            // 준영속 상태가 되어 커밋 시에도 update가 발생하지 않는다.
////            em.detach(member);
//            em.clear();
//
//            // 조회 쿼리가 2번 발생하게 됨.
//            Member member2 = em.find(Member.class, 150L);
//
//            tx.commit();
//        } catch (Exception e) {
//            tx.rollback();
//        } finally {
//            em.close();
//        }
//        emf.close();
//    }
//}
