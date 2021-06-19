package kr.co.doglove.doglove.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.doglove.doglove.domain.User;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static kr.co.doglove.doglove.domain.QUser.user;

@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    @Override
    public List<User> findCustom(Map<String, Object> params) {
//        String name = params.get("name").toString();
//        Integer age = Integer.parseInt(params.get("age").toString());
//        String sex = params.get("sex").toString();
        String name = params.containsKey("name") ? params.get("name").toString() : null;
        Integer age = params.containsKey("age") ? Integer.parseInt(params.get("age").toString()) : null;
        String sex = params.containsKey("sex") ? params.get("sex").toString() : null;

        return queryFactory
                .selectFrom(user)
                .where(nameEq(name), ageEq(age), sexEq(sex))
                .fetch();
    }

    @Override
    public List<User> findCustomJpql(Map<String, Object> params) {
        StringBuffer sb = new StringBuffer();
        sb.append("select u from User u where 1=1");
        if (params.containsKey("name")) {
            sb.append(" and u.name = :name ");
        }

        return em.createQuery(sb.toString(), User.class)
                .setParameter("name", params.get("name").toString())
                .getResultList();
    }

    private BooleanExpression nameEq(String name) {
        return name == null ? null : user.name.eq(name);
    }

    private BooleanExpression sexEq(String sex) {
        return sex == null ? null : user.sex.eq(sex);
    }

    private BooleanExpression ageEq(Integer age) {
        return age == null ? null : user.age.eq(age);
    }
}
