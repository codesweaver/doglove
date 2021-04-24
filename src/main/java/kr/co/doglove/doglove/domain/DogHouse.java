package kr.co.doglove.doglove.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class DogHouse {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
}
