package codesquad.web;

import codesquad.domain.Answer;
import codesquad.domain.Question;
import codesquad.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ApiAnswerAcceptanceTest extends AcceptanceTest {
    private static final Logger log =  LoggerFactory.getLogger(ApiAnswerAcceptanceTest.class);
    private User writer;
    private Question question;
    private Answer answer;

    @Before
    public void setUp() {
        writer = new User("javajigi", "password1", "jimmy", "jaeyeon93@naver.com");
        question = new Question(3L, "question title", "question content", writer);
//        answer = new Answer(3L, writer, question,"답글12345");
    }

    @Test
    public void create() throws Exception {
        ResponseEntity<String> response = basicAuthTemplate().postForEntity("/api/questions", question , String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        String location = response.getHeaders().getLocation().getPath();
        Question dbQuestion = basicAuthTemplate(writer).getForObject(location, Question.class);
        assertThat(dbQuestion, is(question));

        Answer answer = new Answer(3L, writer, question,"답글12345");
        response = basicAuthTemplate().postForEntity(String.format("/api/questions/%d/answer", dbQuestion.getId()), answer, String.class);
        location = response.getHeaders().getLocation().getPath();
        Answer answerdb = basicAuthTemplate(writer).getForObject(location,  Answer.class);
        assertThat(answerdb, is(answer));
    }

    @Test
    public void delete() throws Exception {
        ResponseEntity<String> response = basicAuthTemplate().postForEntity("/api/questions", question , String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        String location = response.getHeaders().getLocation().getPath();
        Question dbQuestion = basicAuthTemplate(writer).getForObject(location, Question.class);
        Answer answer = new Answer(3L, writer, question,"답글12345");
        response = basicAuthTemplate().postForEntity(String.format("/api/questions/%d/answer", dbQuestion.getId()), answer, String.class);
        location = response.getHeaders().getLocation().getPath();
        basicAuthTemplate().delete(location);
        // location is /api/questions/3/answer/3
        response = template().getForEntity(location, String.class);
        assertThat(response.getStatusCode().value(), is(500));
    }
}
