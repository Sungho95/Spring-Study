package hellojpa.jpql;

import javax.persistence.*;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            Member result = em.createQuery("select m from Member m where m.username = :username", Member.class)
                            .setParameter("username", "member1")
                            .getSingleResult();

            System.out.println("result = " + result.getUsername());

//            TypedQuery<Member> query1 = em.createQuery("select m from Member m", Member.class);
//            List<Member> resultList = query1.getResultList();
//
//            for (Member m : resultList) {
//                System.out.println("m = " + m.getUsername());
//
//            }
//
//            TypedQuery<Member> query2 = em.createQuery("select m from Member m where m.id = 1L", Member.class);
//            Member singleResult = query2.getSingleResult();
//            System.out.println("singleResult = " + singleResult.getUsername());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}