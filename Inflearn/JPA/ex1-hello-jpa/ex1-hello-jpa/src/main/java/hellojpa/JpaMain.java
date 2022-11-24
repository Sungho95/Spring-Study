package hellojpa;

import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // 값 타입 저장
            Member member = new Member();
            member.setName("member1");
            member.setHomeAddress(new Address("homeCity", "street", "10000"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("피자");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("보쌈");

            member.getAddressHistory().add(new Address("old1", "street", "10000"));
            member.getAddressHistory().add(new Address("old2", "street", "10000"));

            em.persist(member);

            em.flush();
            em.clear();

            // 값 타입 조회
//            System.out.println("===============START===============");
//            Member findMember = em.find(Member.class, member.getId());
//
//            List<Address> addressHistory = findMember.getAddressHistory();
//            for (Address address : addressHistory) {
//                System.out.println("address = " + address.getCity());
//            }
//
//            Set<String> favoriteFoods = findMember.getFavoriteFoods();
//            for (String favoriteFood : favoriteFoods) {
//                System.out.println("favoriteFood = " + favoriteFood);
//            }

            // 값 타입 수정
            Member findMember = em.find(Member.class, member.getId());

            // 임베디드 값 타입 수정
            Address old = findMember.getHomeAddress();
            findMember.setHomeAddress(new Address("newCity", old.getStreet(), old.getZipcode()));

            // 기본 값 타입 컬렉션 수정
            Set<String> favoriteFoods = findMember.getFavoriteFoods();
            favoriteFoods.remove("치킨");
            favoriteFoods.add("김치볶음밥");

            // 임베디드 값 타입 컬렉션 수정
            List<Address> addressHistory = findMember.getAddressHistory();
            addressHistory.remove(new Address("old1", "street", "10000"));
            addressHistory.add(new Address("newCity1", "street", "10000"));

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}