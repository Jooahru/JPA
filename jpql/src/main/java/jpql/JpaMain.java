package jpql;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

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
            member.setUserName("member1");
            member.setAge(10);
            member.setType(MemberType.ADMIN);

            member.setTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            //String query = "select m from Member m inner join m.team t";
            //String query = "select m from Member m left outer join m.team t";
//            String query = "select m from Member m, Team t where m.userName = t.name";
//            String query = "select m from Member m left join m.team t on t.name = 'teamA'";
            //String query = "select m from Member m left join Team t on m.userName = t.name";
            String query = "select m.userName, 'HELLO', TRUE from Member m " +
                //"where m.type = jpql.MemberType.ADMIN";
                "where m.type = :userType";
            List<Object[]> result = em.createQuery(query).setParameter("userType", MemberType.ADMIN)
                .getResultList();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            System.out.println("e = " + e);
        } finally {
            em.close();
        }

        emf.close();
    }
}
