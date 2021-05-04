package kr.co.doglove.doglove.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter @Setter
public class MemberDog {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_dog_id")
    private Long id;
    private Integer age;
    private String name;
    private LocalDate regDate;
    private String sex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dog_id")
    private Dog dog;
}
