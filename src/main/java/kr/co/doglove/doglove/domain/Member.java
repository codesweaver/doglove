package kr.co.doglove.doglove.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;
    private String email;
    private LocalDateTime regDate;
    private LocalDateTime loginDate;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberPet> memberPets = new ArrayList<>();

    @Embedded
    private Address address;

    //==연관관계 메서드==//
    public void addMemberPet(MemberPet memberPet){
        memberPet.setMember(this);
        this.memberPets.add(memberPet);
    }

}
