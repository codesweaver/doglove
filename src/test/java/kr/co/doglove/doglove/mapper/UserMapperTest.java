package kr.co.doglove.doglove.mapper;

import kr.co.doglove.doglove.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserMapperTest {

    @Autowired
    private UserMapper mapper;

    @Test
    void 마이바티스_테스트() {
        Map<String, Object> params = Map.of(
                "sex", "남"
        );

        List<User> userList = mapper.findByBatis(params);
        for (User user : userList) {
            System.out.println(user);
        }
    }
}