package kr.co.doglove.doglove.etc;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryFactory;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.doglove.doglove.domain.*;
import kr.co.doglove.doglove.dto.MemberDto;
import kr.co.doglove.doglove.dto.QMemberDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static kr.co.doglove.doglove.domain.QGoods.goods;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
//import static kr.co.doglove.doglove.domain.QUser;

@SpringBootTest
@Transactional
public class QueryDslTest {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private JPAQueryFactory queryFactory;

    @Test
    void JPQL_멤버_검색() {
        List<User> resultList = em.createQuery("select u from User u where u.name = :name", User.class)
                .setParameter("name", "김용수")
                .getResultList();
       assertTrue(resultList.stream().allMatch(m -> m.getName().equals("김용수")));
    }

    private QUser user;
    private QCompany company;
    private QOrder order;
    private QOrderItem orderItem;

    @BeforeEach
    void setup() {
        user = new QUser("u");
        company = new QCompany("c");
        order = new QOrder("o");
        orderItem = new QOrderItem("oi");
    }

    @Test
    void QueryDSL_멤버_검색() {

        List<User> resultList = queryFactory
                .select(user)
                .from(user)
                .where(user.name.eq("김용수"))
                .fetch();

        assertTrue(resultList.stream().allMatch(m -> m.getName().equals("김용수")));
    }

    @Test
    void 나이30살이상_여자_검색() {
        int age = 30;
        String sex = "여";

        List<User> resultList = queryFactory
                .selectFrom(user)
                .where(user.age.gt(age).and(user.sex.eq(sex)))
                .fetch();

        assertTrue(resultList.stream().allMatch(u -> u.getAge() > age && u.getSex().equals(sex)));
    }

    @Test
    void 나이30살이상_여자_검색_파라미터() {
        int age = 30;
        String sex = "여";

        List<User> resultList = queryFactory
                .selectFrom(user)
                .where(
                        user.age.gt(age),
                        user.sex.eq(sex)
                )
                .fetch();

        assertTrue(resultList.stream().allMatch(u -> u.getAge() > age && u.getSex().equals(sex)));
    }

    @Test
    void QueryDSL_페이지_테스트() {
        int offset = 0;
        long limit = 5L;

        QueryResults<User> results = queryFactory
                .selectFrom(user)
                .offset(offset)
                .limit(limit)
                .fetchResults();

        assertAll("페이징 테스트",
                () -> assertTrue(results.getTotal() == 15),
                () -> assertTrue(results.getLimit() == 5),
                () -> assertTrue(results.getOffset() == 0)
                );
    }

    @Test
    void QueryDSL_집합() {
        Tuple results = queryFactory
                .select(
                        user.count(),
                        user.age.sum(),
                        user.age.avg(),
                        user.age.max(),
                        user.age.min()
                )
                .from(user)
                .fetchOne();

        System.out.println(results.get(user.count()));
        System.out.println(results.get(user.age.sum()));
        System.out.println(results.get(user.age.avg()));
        System.out.println(results.get(user.age.max()));
        System.out.println(results.get(user.age.min()));
    }

    @Test
    void QeuryDSL_그룹바이() {
        List<Tuple> results = queryFactory
                .select(
                        user.sex,
                        user.count(),
                        user.age.sum(),
                        user.age.avg(),
                        user.age.max(),
                        user.age.min()
                )
                .from(user)
                .groupBy(user.sex)
//                .having()
                .fetch();

        for (Tuple result : results) {
            System.out.print(result.get(user.sex));
            System.out.print(result.get(user.count()));
            System.out.print(result.get(user.age.sum()));
            System.out.print(result.get(user.age.avg()));
            System.out.print(result.get(user.age.max()));
            System.out.println(result.get(user.age.min()));
        }
    }

    @Test
    void inner_join_member_company() {
        List<Order> results = queryFactory
                .selectFrom(order)
                .join(order.orderItems, orderItem)
                .where(orderItem.quantity.eq(1))
                .fetch();
        assertTrue(results.stream().allMatch(o -> o.getOrderItems().stream().anyMatch(i -> i.getQuantity() == 1)));
    }

    @Test
    void theta_join() {
        List<User> results = queryFactory
                .select(user)
                .from(order, user)
                .where(order.orderName.eq(user.name))
                .fetch();
        for (User user : results) {
            System.out.println(user.toString());
        }
    }

    @Test
    void on_사용하기() {
        List<Tuple> results = queryFactory
                .select(user, company)
                .from(user)
                .leftJoin(user.company, company).on(company.name.eq("코리아센터"))
                .fetch();
        for (Tuple result : results) {
            System.out.println(result.toString());
        }
    }

    @Test
    void left조인_연관관계없음() {
        List<Tuple> results = queryFactory
                .select(user, order)
                .from(user)
                .leftJoin(order).on(order.orderName.eq(user.name))
                .fetch();
        for (Tuple result : results) {
            System.out.println(result.toString());
        }
    }

    @Test
    void 엔플러스원_문제() {
        List<Order> orders = queryFactory
                .selectFrom(order)
                .fetch();
        for (Order order: orders) {
            System.out.println(order.getOrderItems().toString());
        }
    }

    @Test
    void fetch_join_해결법() {
        List<Order> results = queryFactory
                .selectFrom(order)
                .join(order.orderItems, orderItem).fetchJoin()
                .join(orderItem.goods, goods).fetchJoin()
                .fetch();
        for (Order order : results) {
            System.out.println(order.toString());
        }
    }

    @Test
    void WHERE_서브쿼리() {
        QUser userSub = new QUser("userSub");

        List<User> results = queryFactory
                .selectFrom(user)
                .where(user.age.eq(
                        JPAExpressions
                                .select(userSub.age.min())
                                .from(userSub)
                ))
                .fetch();
        for(User user : results) {
            System.out.println(user.toString());
        }
    }

    @Test
    void SELECT_서브쿼리() {
        QUser userSub = new QUser("userSub");

        List<Tuple> results = queryFactory
                .select(user, JPAExpressions
                        .select(userSub.age.avg())
                        .from(userSub))
                .from(user)
                .fetch();
        for (Tuple result : results) {
            System.out.println(result.get(user).toString());
        }
    }

    @Test
    void CASE() {
        List<String> results = queryFactory
                .select(user.age
                        .when(44).then("용수나이")
                        .when(36).then("상영나이")
                        .otherwise("어린것들")
                )
                .from(user)
                .fetch();
        for(String result: results) {
            System.out.println(result);
        }
    }

    @Test
    void CASE_WHEN_TEST() {

        NumberExpression<Integer> rankPath = new CaseBuilder()
                .when(user.age.between(20, 30)).then(3)
                .when(user.age.between(31, 40)).then(1)
                .when(user.age.between(41, 50)).then(2)
                .otherwise(9);

        StringExpression rankString = new CaseBuilder()
                .when(user.age.between(20, 30)).then("좋을때다")
                .when(user.age.between(31, 40)).then("건강관리하자")
                .when(user.age.between(41, 50)).then("이젠 조심해야지")
                .otherwise("사망");

        List<Tuple> resultList = queryFactory
                .select(user.name, user.age, rankString)
                .from(user)
                .orderBy(rankPath.asc())
                .fetch();

        for (Tuple result : resultList){
            System.out.printf("이름: %s, 나이:%d, 상태: %s\n",
                    result.get(user.name),
                    result.get(user.age),
                    result.get(rankString));
        }
    }

    @Test
    void 상수표현() {
        List<Tuple> resultList = queryFactory
                .select(user.name, user.age, Expressions.constant("A"))
                .from(user)
                .fetch();
        for (Tuple result : resultList) {
            System.out.printf("result = %s\n", result);
        }
    }

    @Test
    void 콘캣테스트() {
        List<Tuple> resultList = queryFactory
                .select(user.name, user.age, user.name.concat("_").concat(user.age.stringValue()))
                .from(user)
                .fetch();
        for (Tuple result : resultList) {
            System.out.printf("result = %s\n", result);
        }
    }

    @Test
    void 단일갑프로젝션() {
        List<String> fetch = queryFactory
                .select(user.name)
                .from(user)
                .offset(0).limit(5)
                .fetch();
        for (String s : fetch) {
            System.out.printf("s = %s\n", s);
        }
    }

    @Test
    void 복수값프로젝션() {
        List<Tuple> fetch = queryFactory
                .select(user.name, user.age)
                .from(user)
                .offset(0).limit(5)
                .fetch();
        for (Tuple tuple : fetch) {
            String s = tuple.get(user.name);
            Integer i = tuple.get(user.age);
            System.out.printf("name = %s, age = %d\n", s, i);
        }
    }

    @Test
    void 프로젝션DTO_빈으로_사용하기() {
        List<MemberDto> fetch = queryFactory
                .select(Projections.bean(MemberDto.class,
                        user.name,
                        user.age,
                        user.sex
                ))
                .from(user)
                .offset(0).limit(5)
                .fetch();
        // memberDto.setName(name);
        for (MemberDto memberDto : fetch) {
            System.out.println(memberDto);
        }
    }

    @Test
    void 프로젝션DTO_필드직접적근_사용하기() {
        List<MemberDto> fetch = queryFactory
                .select(Projections.fields(MemberDto.class,
                        user.name,
                        user.age,
                        user.sex
                ))
                .from(user)
                .offset(0).limit(5)
                .fetch();
        // memberDto.name = name;
        for (MemberDto memberDto : fetch) {
            System.out.println(memberDto);
        }
    }

    @Test
    void 프로젝션DTO_이름이다를경우_사용하기() {
        List<MemberDto> fetch = queryFactory
                .select(Projections.fields(MemberDto.class,
                        user.name.as("name"),
                        user.age,
                        user.sex
                ))
                .from(user)
                .offset(0).limit(5)
                .fetch();
        for (MemberDto memberDto : fetch) {
            System.out.println(memberDto);
        }
    }

    @Test
    void 프로젝션DTO_생성자_사용하기() {
        // 파라미터의 순서만 일치한다면 필드 이름이 다른 문제는 무시한다
        List<MemberDto> fetch = queryFactory
                .select(Projections.constructor(MemberDto.class,
                        user.name,
                        user.age,
                        user.sex
                ))
                .from(user)
                .offset(0).limit(5)
                .fetch();
        for (MemberDto memberDto : fetch) {
            System.out.println(memberDto);
        }
    }

    @Test
    void 프로젝션DTO_QClass_사용하기() {
        // 파라미터의 순서만 일치한다면 필드 이름이 다른 문제는 무시한다
        List<MemberDto> fetch = queryFactory
            .select(new QMemberDto(user.name, user.age, user.sex))
            .from(user)
            .offset(0).limit(5)
            .fetch();
        for (MemberDto memberDto : fetch) {
            System.out.println(memberDto);
        }
    }

    @Test
    void distinct_사용하기() {
        List<String> fetch = queryFactory
                .select(user.sex).distinct()
                .from(user)
                .fetch();
        for (String s : fetch) {
            System.out.printf("s = %s\n", s);
        }
    }

    @Test
    void 동적쿼리_불린빌더() {
        Map<String, Object> params = Map.of(
//                "age", 44,
//                "name", "김용수",
                "sex", "남"
        );

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (params.containsKey("age")) {
            booleanBuilder.and(user.age.eq(Integer.parseInt(params.get("age").toString())));
        }
        if (params.containsKey("name")) {
            booleanBuilder.and(user.name.eq(params.get("name").toString()));
        }
        if (params.containsKey("sex")) {
            booleanBuilder.and(user.sex.eq(params.get("sex").toString()));
        }

        List<User> fetch = queryFactory
                .selectFrom(user)
                .where(booleanBuilder)
                .fetch();
        for (User fetch1 : fetch) {
            System.out.println(fetch1);
        }
    }

    @Test
    void 동적쿼리_불린익스프레션() {
//        String name = "김용수";
        String name = null;
        String sex = "남";
//        Integer age = 44;
        Integer age = null;

        List<User> fetch = queryFactory
                .selectFrom(user)
                .where(nameEq(name), sexEq(sex), ageEq(age))
                .fetch();
        for (User fetch1 : fetch) {
            System.out.println(fetch1);
        }
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

    @Test
    void 대량_업데이트() {
        Long count = queryFactory
                .update(user)
                .set(user.sex, "여")
                .set(user.age, user.age.add(2))
                .execute();
        System.out.println(count);

        em.flush();
        em.clear();

        List<User> userList = queryFactory
                .selectFrom(user)
                .where(user.sex.eq("여"))
                .fetch();

        for (User user : userList) {
            System.out.println(user);
        }
    }
}
