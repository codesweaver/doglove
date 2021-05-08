package kr.co.doglove.doglove.repository;

import kr.co.doglove.doglove.domain.Dog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DogRepository {

    private final EntityManager em;

    public Long save(Dog dog) {
        em.persist(dog);
        return dog.getId();
    }

    public Dog findOne(Long id) {
        return em.find(Dog.class, id);
    }

    public List<Dog> findAll() {
        return em.createQuery("SELECT d FROM Dog d", Dog.class)
                .getResultList();
    }

    public List<Dog> findByType(String type) {
        return em.createQuery("SELECT d FROM Dog d WHERE d.type = :type", Dog.class)
                .setParameter("type", type)
                .getResultList();
    }
}
