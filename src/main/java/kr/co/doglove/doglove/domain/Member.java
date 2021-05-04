package kr.co.doglove.doglove.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String email;
    private LocalDateTime regDate;
    private LocalDateTime loginDate;
    private Address address;

    public Member() {

    }

    public Member(String email) {
        this.email = email;
        this.regDate = LocalDateTime.now();
        this.loginDate = LocalDateTime.now();
    }

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberDog> memberDogs = new ArrayList<>();

    //*관계 메서드*//
    public void createMemberDog(MemberDog memberDog) {
        memberDog.setMember(this);
        this.memberDogs.add(memberDog);
    }
}
