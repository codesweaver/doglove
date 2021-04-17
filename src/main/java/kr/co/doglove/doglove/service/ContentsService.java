package kr.co.doglove.doglove.service;

import kr.co.doglove.doglove.repository.ContentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContentsService {

    @Autowired
    private final ContentsRepository contentsRepository;
}
