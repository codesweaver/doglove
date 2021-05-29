package kr.co.doglove.doglove.repository;

import kr.co.doglove.doglove.domain.Member;
import kr.co.doglove.doglove.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpqlUserRepository {

    private final EntityManager em;

    public Long save(User user) {
        em.persist(user);
        return user.getId();
    }

    public Member findById(Long id){
        return em.find(Member.class, id);
    }

    public List<User> findAll(){
        return em.createQuery("select m from User m", User.class).getResultList();
    }

    public List<User> findByName(String name){
        return em.createQuery("select m from User m where m.name = :name")
                .setParameter("name",name)
                .getResultList();
    }

    public List<User> findPage(int offset, int limit) {
        return em.createQuery("select u from User u", User.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public Long count() {
        return em.createQuery("select count(u) from User u", Long.class)
                .getSingleResult();
    }

    public Page<User> findPage(Pageable pageable) {
        int offset = pageable.getPageNumber() * pageable.getPageSize();
        int limit = pageable.getPageSize();
        List<User> memberList = findPage(offset, limit);

        return new PageImpl<User>(memberList, pageable, count());
    }

    public int bulkAgeUpdate(int age) {
        return em.createQuery("update User u set u.age = u.age+1 where u.age>:age")
                .setParameter("age", age)
                .executeUpdate();
    }
}