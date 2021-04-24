package kr.co.doglove.doglove.service;

import kr.co.doglove.doglove.domain.Contents;
import kr.co.doglove.doglove.repository.ContentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContentsService {

    private final ContentsRepository contentsRepository;

    public Contents findOne(Long id){
        return contentsRepository.findById(id).orElse(null);
    }

    public Long save(Contents contents){
        contentsRepository.save(contents);
        return contents.getId();
    }
}
