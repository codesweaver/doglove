package kr.co.doglove.doglove.contoller;


import kr.co.doglove.doglove.domain.Contents;
import kr.co.doglove.doglove.service.ContentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/v1/update/{id}")
    public void updateContents(@PathVariable("id") Long id, @RequestBody Contents contents){
        contentsService.update(id, contents);
    }

    @DeleteMapping("/v1/delete/{id}")
    public void deleteContents(@PathVariable("id") Long id){
        contentsService.delete(id);
    }

    @GetMapping("/v1/list/{name}")
    public List<Contents> findName(@PathVariable("name") String name){
        return contentsService.findByName(name);
    }


}
