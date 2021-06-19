package kr.co.doglove.doglove.mapper;

import kr.co.doglove.doglove.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    List<User> findByBatis(Map<String, Object> params);
}
