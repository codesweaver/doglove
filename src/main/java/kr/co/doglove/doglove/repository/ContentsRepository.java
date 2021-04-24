package kr.co.doglove.doglove.repository;

import kr.co.doglove.doglove.domain.Contents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public interface ContentsRepository extends JpaRepository<Contents, Long> {

}
