package kr.co.doglove.doglove.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@ToString
//@AllArgsConstructor
public class MemberDto {
    private String name;
    private Integer age;
    private String sex;

    @QueryProjection
    public MemberDto(String name, Integer age, String sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }
}
