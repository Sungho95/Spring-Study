package hellojpa.jpql;

import javax.persistence.*;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Team team = new Team();
            team.setName("team1");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            member.setTeam(team);
            member.setType(MemberType.ADMIN);
            em.persist(member);

            em.flush();
            em.clear();

            String query = "select m.username, 'HELLO', TRUE, false from Member m where m.type = :userType";

            List<Object[]> result = em.createQuery(query)
                    .setParameter("userType", MemberType.ADMIN)
                    .getResultList();

            for (Object[] objects : result) {
                System.out.println("objects[0] = " + objects[0]);
                System.out.println("objects[1] = " + objects[1]);
                System.out.println("objects[2] = " + objects[2]);
                System.out.println("objects[3] = " + objects[3]);
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}