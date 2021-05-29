package kr.co.doglove.doglove.repository;

import kr.co.doglove.doglove.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
@Transactional(readOnly = true)
class BoardRepositoryTest {
    @Autowired
    BoardRepository boardRepository;

    @Autowired
    DogRepository dogRepository;

    @Autowired
    JpqlMemberRepository jpqlMemberRepository;

    @Test
    void 보드생성테스트() {

        Dog dog = new Dog();
        dog.setType("치와와");
        dogRepository.save(dog);

        Member member = new Member();
        member.setEmail("codetrain999@gmail.com");
        member.setAddress(new Address("전농로 10길 20", "서울시 동대문구", "02536"));
        member.setRegDate(LocalDateTime.now());
        member.setLoginDate(LocalDateTime.now());

        MemberDog memberDog = new MemberDog();
        memberDog.setDog(dog);
        memberDog.setName("치킨너겟");
        memberDog.setAge(3);
        memberDog.setRegDate(LocalDate.now());
        memberDog.setSex("Male");

        member.createMemberDog(memberDog);
        jpqlMemberRepository.save(member);

        Board board = new Board();
        board.setSubject("테스트 서브젝트");
        board.setContents("테스트 콘텐츠");
        board.setRegDate(LocalDateTime.now());
        board.setMember(member);

        boardRepository.save(board);
    }
}