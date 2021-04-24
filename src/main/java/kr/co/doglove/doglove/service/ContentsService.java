package kr.co.doglove.doglove.service;

import kr.co.doglove.doglove.domain.Contents;
import kr.co.doglove.doglove.repository.ContentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContentsService {

    private final ContentsRepository contentsRepository;

    public Contents findOne(Long id){
        return contentsRepository.findById(id).orElse(null);
    }

    public Long save(Contents contents){
        contentsRepository.save(contents);
        // em.persist(contestns);
        return contents.getId();
    }

    @Transactional
    public void update(Long id, Contents contents){
        Contents findContets = contentsRepository.findById(id).orElse(null);
        findContets.setContent(contents.getContent());
        findContets.setName(contents.getName());
    }

    @Transactional
    public void delete(Long id){
        contentsRepository.deleteById(id);
    }

    public List<Contents> findByName(String name){
        return contentsRepository.findByName(name);
    }
}
