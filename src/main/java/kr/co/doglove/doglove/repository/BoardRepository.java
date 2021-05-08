package kr.co.doglove.doglove.repository;

import kr.co.doglove.doglove.domain.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepository {

    private final EntityManager em;

    public Long save(Board board) {
        em.persist(board);
        return board.getId();
    }

    public Board findOne(Long id) {
        return em.find(Board.class, id);
    }

    public List<Board> findAll() {
        return em.createQuery("SELECT b FROM Board b", Board.class)
                .getResultList();
    }

    public List<Board> findBySubject(String subject) {
        return em.createQuery("SELECT b FROM Board b WHERE b.subject = :subject", Board.class)
                .setParameter("subject", subject)
                .getResultList();
    }
}
