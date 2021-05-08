package kr.co.doglove.doglove.repository;

import kr.co.doglove.doglove.domain.MemberDog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberDogRepository {

    private final EntityManager em;

    public Long save(MemberDog memberDog) {
        em.persist(memberDog);
        return memberDog.getId();
    }

    public MemberDog findOne(Long id) {
        return em.find(MemberDog.class, id);
    }

    public List<MemberDog> findAll() {
        return em.createQuery("SELECT m FROM MemberDog m", MemberDog.class)
                .getResultList();
    }

    public List<MemberDog> findByName(String name) {
        return em.createQuery("SELECT m FROM MemberDog m WHERE m.name = :name", MemberDog.class)
                .setParameter("name", name)
                .getResultList();
    }
}
