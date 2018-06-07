package codesquad.web;

import codesquad.CannotDeleteException;
import codesquad.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import support.test.AcceptanceTest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ApiDeleteAcceptanceTest extends AcceptanceTest {
    private static final Logger log =  LoggerFactory.getLogger(ApiDeleteAcceptanceTest.class);
    private Question question;
    private User JIMMY;
    private Answer answer;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Before
    public void setUp() {
        JIMMY = new User("jimmy", "12345", "jimmy", "jaeyeon93@naver.com");
    }

    @Test
    public void 답변없음로그인유저같음() throws Exception {
        question = new Question(3L,"제목112", "내용119", defaultUser());
        String path = createResource("/api/questions", question, defaultUser());
        log.info("path is : {}", path);
        assertThat(getResource(path, Question.class, defaultUser()), is(question));
        basicAuthTemplate().delete(path);
        assertTrue(getResource(path, Question.class, defaultUser()).isDeleted());
    }

    @Test
    public void 답변없음로그인유저다름() throws Exception {
        question = new Question(3L,"제목112", "내용119", defaultUser());
        String path = createResource("/api/questions", question, defaultUser());
        log.info("question : {}", question.toString());
        basicAuthTemplate(JIMMY).delete(path);
        assertFalse(getResource(path, Question.class, defaultUser()).isDeleted());
    }

    @Test
    public void 답변있음글쓴이같음() throws Exception {
        question = new Question(3L,"제목112", "내용119", defaultUser());
        String path = createResource("/api/questions", question, defaultUser());
        log.info("path is : {}", path);
        assertThat(getResource(path, Question.class, defaultUser()), is(question));
        log.info("question is {}", question);
        Answer answer = new Answer(7L, defaultUser(), question,"답글12345");
        log.info("answer is {}", answer.toString());
        path = createResource(String.format("/api/questions/%d/answers", getResource(path, Question.class, defaultUser()).getId()), answer, defaultUser());
        assertThat(getResource(path, Answer.class, defaultUser()), is(answer));
        basicAuthTemplate().delete(path);
        log.info("isDeleted is : {}", question.isDeleted());
        assertFalse(getResource(path, Question.class, defaultUser()).isDeleted());
    }

    @Test
    public void 답변있음로그인유저다름() throws Exception {
        question = new Question(3L,"제목112", "내용119", defaultUser());
        String path = createResource("/api/questions", question, defaultUser());
        assertThat(getResource(path, Question.class, defaultUser()), is(question));
        log.info("question is {}", question);
        Answer answer = new Answer(7L, defaultUser(), question,"답글12345");
        log.info("answer is {}", answer.toString());
        path = createResource(String.format("/api/questions/%d/answers", getResource(path, Question.class, defaultUser()).getId()), answer, defaultUser());
        assertThat(getResource(path, Answer.class, defaultUser()), is(answer));
        basicAuthTemplate(JIMMY).delete(path);
        question = getResource(path, Question.class, defaultUser());
        log.info("isDeleted is : {}", question.isDeleted());
        assertFalse(getResource(path, Question.class, defaultUser()).isDeleted());
    }

    @Test
    public void 답변여러개로그인같음() throws Exception {
        question = new Question(3L,"제목112", "내용119", defaultUser());
        String path = createResource("/api/questions", question, defaultUser());
        assertThat(getResource(path, Question.class, defaultUser()), is(question));
        log.info("question is {}", question);
        Answer answer = new Answer(7L, defaultUser(), question,"답글12345");
        Answer answer2 = new Answer(8L, JIMMY, question, "두번째 댓글");
        log.info("answer is {}", answer.toString());
        log.info("answer2 is {} ", answer2.toString());
        path = createResource(String.format("/api/questions/%d/answers", getResource(path, Question.class, defaultUser()).getId()), answer, defaultUser());
        assertThat(getResource(path, Answer.class, defaultUser()), is(answer));
        basicAuthTemplate(defaultUser()).delete(path);
        question = getResource(path, Question.class, defaultUser());
        log.info("isDeleted is : {}", question.isDeleted());
        assertFalse(getResource(path, Question.class, defaultUser()).isDeleted());
    }
}
