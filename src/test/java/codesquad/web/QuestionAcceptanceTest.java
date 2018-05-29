package codesquad.web;

import codesquad.converter.HtmlFormDataBuilder;
import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;
import codesquad.domain.User;
import codesquad.domain.UserRepository;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import java.util.logging.LogManager;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class QuestionAcceptanceTest extends AcceptanceTest {
    private static final Logger log =  LoggerFactory.getLogger(QuestionAcceptanceTest.class);

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void create() throws Exception {
        ResponseEntity<String> response = template().getForEntity("/questions/form", String.class);
        log.info("status code : {}", response.getStatusCode());
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        log.debug("body : {}", response.getBody());
    }

    @Test
    public void makeQuestion() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        User loginUser = defaultUser();
        Question question = new Question("제목111", "내용111");
        HtmlFormDataBuilder htmlFormDataBuilder = HtmlFormDataBuilder.urlEncodedForm()
                .addParams("title", "제목1111")
                .addParams("contents", "내용1111");
        ResponseEntity<String> response = basicAuthTemplate(loginUser).postForEntity("/questions", htmlFormDataBuilder.build(), String.class);
//        assertThat(response.getHeaders().getLocation().getPath(), is("/questions"));
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
//        assertNotNull(userRepository.findByUserId(userId));
//        assertThat(response.getHeaders().getLocation().getPath(), is("/users"));
    }

    @Test
    public void list() throws Exception {
        ResponseEntity<String> response = template().getForEntity("/questions", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        log.debug("body : {}", response.getBody());
    }

    @Test
    public void showQnA() throws Exception {
        int id = 1;
        ResponseEntity<String> response = template().getForEntity(String.format("/questions/%d", id), String.class);
        log.info("body : {}", response.getBody());
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void updateQnA() throws Exception {
        User writer = userRepository.findByUserId("javajigi").get();
        Question question = questionRepository.getOne(writer.getId());
        ResponseEntity<String> response = basicAuthTemplate(writer).getForEntity(String.format("/questions/%d/form", question.getId()), String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }
}
