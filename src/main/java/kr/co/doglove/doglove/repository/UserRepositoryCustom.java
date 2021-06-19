package kr.co.doglove.doglove.repository;

import kr.co.doglove.doglove.domain.Member;
import kr.co.doglove.doglove.domain.User;

import java.util.List;
import java.util.Map;

public interface UserRepositoryCustom {
    public List<User> findCustom(Map<String, Object> params);
    public List<User> findCustomJpql(Map<String, Object> params);
}
