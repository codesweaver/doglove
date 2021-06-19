package kr.co.doglove.doglove.repository;

import kr.co.doglove.doglove.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    @Query("select u from User u")
    Page<User> findPage(Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("update User u set u.age = u.age+1 where u.age>:age")
    int bulkAgeUpdate(@Param("age") int age);
}
