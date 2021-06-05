package kr.co.doglove.doglove.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@ToString(of={"name"})
@NoArgsConstructor
public class Company {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="company_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "company")
    private List<User> members = new ArrayList<>();

    public Company(String name) {
        this.name = name;
    }

    public void addUser(User user) {
        user.setCompany(this);
        this.members.add(user);
    }
}
