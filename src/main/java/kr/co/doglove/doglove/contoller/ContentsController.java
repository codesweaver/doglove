package kr.co.doglove.doglove.contoller;


import kr.co.doglove.doglove.domain.Contents;
import kr.co.doglove.doglove.service.ContentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contents")
@RequiredArgsConstructor
public class ContentsController {

    private final ContentsService contentsService;

    @GetMapping("/v1/contents/{id}")
    public Contents getInfo(@PathVariable(name="id") Long id){
        return contentsService.findOne(id);
    }

    @PostMapping("/v1/postcontents")
    public Long setContents(@RequestBody Contents contents){
        Long id = contentsService.save(contents);
        return id;
    }
}
