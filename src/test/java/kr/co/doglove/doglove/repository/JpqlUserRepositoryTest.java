package kr.co.doglove.doglove.repository;

import kr.co.doglove.doglove.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class JpqlUserRepositoryTest {

    @Autowired
    private JpqlUserRepository userRepository;

    @Test
    void 페이징_테스트() {
        int page = 1;
        int limit = 5;
        int offset = (page-1) * limit;

        List<User> users = userRepository.findPage(offset, limit);
        Long count = userRepository.count();

        assertAll("멤버 페이지 테스트",
                () -> assertTrue(users.size() == 5, "회원수가 다섯이 아닙니다"),
                () -> assertEquals(count, 15L)

        );
    }

    @Autowired
    private JpaUserRepository jpaUserRepository;
//    private JpqlUserRepository jpaUserRepository;

    @Test
    void JPA_페이지_테스트() {
        int page = 1;
        int limit = 5;

//        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        Sort sort = Sort.by(Sort.Direction.DESC, "name")
                .and(Sort.by(Sort.Direction.ASC, "age"));
        PageRequest pageable = PageRequest.of(page-1, limit, sort);
        Page<User> memberPage = jpaUserRepository.findPage(pageable);
        List<User> memberList = memberPage.getContent();
        long count = memberPage.getTotalElements();

        for (User u : memberList) {
            System.out.printf("name=%s, age=%d\n", u.getName(), u.getAge());
        }

        assertAll("멤버 페이지 테스트",
                () -> assertTrue(memberList.size() == limit, "회원수가 5가 아닙니다"),
                () -> assertEquals(count, 15L)
                // () -> assertEquals(memberList.get(0).getName(), "김용수")
                );
    }

    @Test
    void 벌크_업데이트_테스트() {
        int i = jpaUserRepository.bulkAgeUpdate(20);
        assertTrue(i == 15, "뭔가 잘못됬는디요...?");
    }
}