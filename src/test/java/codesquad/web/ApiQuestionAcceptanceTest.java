package codesquad.web;

import codesquad.domain.Question;
import codesquad.domain.User;
import codesquad.dto.QuestionDto;
import codesquad.dto.UserDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import support.test.AcceptanceTest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ApiQuestionAcceptanceTest extends AcceptanceTest {
    private static final Logger log =  LoggerFactory.getLogger(ApiQuestionAcceptanceTest.class);
    private User writer;
    private Question question;

    @Before
    public void setUp() {
        writer = new User("javajigi", "12345", "jimmy", "jaeyeon93@naver.com");
        question = new Question(3L,"제목11", "내용11", writer);
    }

    @Test
    public void create() throws Exception {
        ResponseEntity<String> response = basicAuthTemplate().postForEntity("/api/questions", question , String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        String location = response.getHeaders().getLocation().getPath();
        // location is /api/questions/3
        Question dbQuestion = basicAuthTemplate(writer).getForObject(location, Question.class);
        assertThat(dbQuestion, is(question));
    }

    @Test
    public void show() throws Exception {
        ResponseEntity<String> response = basicAuthTemplate().getForEntity("/api/questions", String.class);
    }
}
