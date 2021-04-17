package kr.co.doglove.doglove.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Contents {
    @Id
    private Long id;
    private String name;
    private String content;
    private LocalDateTime createDate;
}
