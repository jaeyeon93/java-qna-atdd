package codesquad.web;

import codesquad.CannotDeleteException;
import codesquad.domain.*;
import codesquad.security.LoginUser;
import codesquad.service.QnaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.net.URI;

@RestController
@RequestMapping("/api/questions/{questionId}/answer")
public class ApiAnswerController {
    private static final Logger log =  LoggerFactory.getLogger(ApiAnswerController.class);

    @Resource(name = "qnaService")
    private QnaService qnaService;

    @GetMapping("{id}")
    public Answer show(@PathVariable long id) {
        return qnaService.showAnswer(id);
    }

    @PostMapping("")
    public ResponseEntity<Void> create(@LoginUser User loginUser, @PathVariable long questionId, String contents) {
        Answer createAnswer = qnaService.addAnswer(loginUser, questionId, contents);
        log.info("controller contents is {}", contents);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(String.format("/api/questions/%d/answer/", questionId) + createAnswer.getId()));
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @DeleteMapping("{id")
    public void delete(@LoginUser User loginUser, @PathVariable long id) throws CannotDeleteException {
        log.f
        qnaService.deleteAnswer(loginUser, id);
    }
}
