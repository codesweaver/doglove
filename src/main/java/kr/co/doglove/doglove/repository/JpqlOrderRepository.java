package kr.co.doglove.doglove.repository;

import kr.co.doglove.doglove.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpqlOrderRepository {

    private final EntityManager em;

    public Long save(Order order) {
        em.persist(order);
        return order.getId();
    }

//    public Order findOne(Long id) {
//        return em.createNamedQuery("Order.findOne", Order.class)
//                .setParameter("id", id)
//                .getSingleResult();
//    }

    public Optional<Order> findById(Long id) {
//        return em.find(Order.class, id);
        return Optional.ofNullable(em.find(Order.class, id));
    }

    public List<Order> findAll() {
        return em.createQuery("SELECT o FROM Order o", Order.class)
                .getResultList();
    }

    public List<Order> findByOrderName(String orderName) {
        return em.createQuery("SELECT o FROM Order o WHERE o.orderName = :orderName", Order.class)
                .setParameter("orderName", orderName)
                .getResultList();
    }
}
