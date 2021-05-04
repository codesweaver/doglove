package kr.co.doglove.doglove.repository;

import kr.co.doglove.doglove.domain.Address;
import kr.co.doglove.doglove.domain.Dog;
import kr.co.doglove.doglove.domain.Member;
import kr.co.doglove.doglove.domain.MemberDog;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {

    }

    @Test
    void save() {
        //given
        Member member = new Member("codetrain999@gmail.com");
        Long memberId = memberRepository.save(member);

        //when
        Member foundMember = memberRepository.findOne(memberId);

        //then
        Assertions.assertThat(foundMember).isEqualTo(member);
    }

    @Test
    void findAll() {
        //given
        Member member1 = new Member("codetrain999@gmail.com");
        Member member2 = new Member("namakemonobistro@gmail.com");

        memberRepository.save(member1);
        memberRepository.save(member2);

        //when
        List<Member> members = memberRepository.findAll();

        //then
        Assertions.assertThat(members.size()).isEqualTo(2);
        Assertions.assertThat(members.get(0).getEmail()).isEqualTo("codetrain999@gmail.com");
    }

    @Test
    void findByEmail() {
        //given
        Member member1 = new Member("codetrain999@gmail.com");
        Member member2 = new Member("namakemonobistro@gmail.com");

        memberRepository.save(member1);
        memberRepository.save(member2);

        //when
        Member foundMember = memberRepository.findByEmail("codetrain999@gmail.com");

        //then
        Assertions.assertThat(foundMember).isEqualTo(member1);
    }
}