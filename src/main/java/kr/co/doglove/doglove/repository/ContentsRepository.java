package kr.co.doglove.doglove.repository;

import kr.co.doglove.doglove.domain.Contents;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ContentsRepository {

    @PersistenceContext
    private EntityManager em;

    public Contents findOne(Long id) {
        return em.find(Contents.class, id);
    }
}
