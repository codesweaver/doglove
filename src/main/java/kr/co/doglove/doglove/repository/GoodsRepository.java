package kr.co.doglove.doglove.repository;

import kr.co.doglove.doglove.domain.Goods;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class GoodsRepository {

    private final EntityManager em;

    public Long save(Goods goods) {
        em.persist(goods);
        return goods.getId();
    }

    public Goods findOne(Long id) {
        return em.find(Goods.class, id);
    }

    public List<Goods> findAll() {
        return em.createQuery("SELECT g FROM Goods g", Goods.class)
                .getResultList();
    }

    public List<Goods> findByName(String name) {
        return em.createQuery("SELECT g FROM Goods g WHERE g.name = :name", Goods.class)
                .setParameter("name", name)
                .getResultList();
    }
}
