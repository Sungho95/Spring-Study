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
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("teamA");
            member.setAge(10);
            member.setTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            String jpql = "select t from Team t join fetch t.members where t.name = 'teamA'";

            List<Team> teams = em.createQuery(jpql, Team.class).getResultList();

            for (Team team1 : teams) {
                System.out.println("teamname = " + team1.getName() + ", " +
                        "team = " + team1);

                for (Member member1 : team1.getMembers()) {
                    System.out.println("->username = " + member1.getUsername() + ", " +
                        "member = " + member1);
                }
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