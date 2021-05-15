package kr.co.doglove.doglove.repository;

import kr.co.doglove.doglove.domain.Dog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional(readOnly = true)
class DogRepositoryTest {

    @Autowired
    DogRepository dogRepository;

    @Test
    void 개생성테스트() {
        Dog dog = new Dog();
        dog.setType("치와와");

        Long dogId = dogRepository.save(dog);
        Dog foundDog = dogRepository.findOne(dogId);

        assertEquals(foundDog, dog);
    }
}