package kr.co.doglove.doglove.repository;

import kr.co.doglove.doglove.domain.Contents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public interface ContentsRepository extends JpaRepository<Contents, Long> {
    List<Contents> findByName(String name);
    /*
    public List<Contents> findByName(String name){
        em.creteQuery("select c fomr Contetns c where name = :name").setParamenter("name",name").getResultList();
    }
     */
}
