package kr.co.doglove.doglove.repository;

import kr.co.doglove.doglove.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
class JpqlMemberRepositoryTest {

    @Autowired
    JpqlMemberRepository jpqlMemberRepository;

    @BeforeEach
    public void beforeEach() {

    }

    @Test
    void save() {
        //given
        Member member = new Member("codetrain999@gmail.com");
        Long memberId = jpqlMemberRepository.save(member);

        //when
        Member foundMember = jpqlMemberRepository.findOne(memberId);

        //then
        Assertions.assertThat(foundMember).isEqualTo(member);
    }

    @Test
    void findAll() {
        //given
        Member member1 = new Member("codetrain999@gmail.com");
        Member member2 = new Member("namakemonobistro@gmail.com");

        jpqlMemberRepository.save(member1);
        jpqlMemberRepository.save(member2);

        //when
        List<Member> members = jpqlMemberRepository.findAll();

        //then
        Assertions.assertThat(members.size()).isEqualTo(2);
        Assertions.assertThat(members.get(0).getEmail()).isEqualTo("codetrain999@gmail.com");
    }

    @Test
    void findByEmail() {
        //given
        Member member1 = new Member("codetrain999@gmail.com");
        Member member2 = new Member("namakemonobistro@gmail.com");

        jpqlMemberRepository.save(member1);
        jpqlMemberRepository.save(member2);

        //when
        Member foundMember = jpqlMemberRepository.findByEmail("codetrain999@gmail.com");

        //then
        Assertions.assertThat(foundMember).isEqualTo(member1);
    }
}