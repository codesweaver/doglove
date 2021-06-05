package kr.co.doglove.doglove.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@ToString(of = {"name", "age", "sex"})
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long id;
    private String name;
    private int age;
    private String sex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @Builder
    public User(String name, int age, String sex){
        this.name = name;
        this.age = age;
        this.sex = sex;
    }
}