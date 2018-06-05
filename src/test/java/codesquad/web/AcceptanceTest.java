package codesquad.web;

import codesquad.CannotDeleteException;
import codesquad.domain.AnswerRepository;
import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;
import codesquad.domain.User;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class AcceptanceTest extends support.test.AcceptanceTest {
    private static final Logger log =  LoggerFactory.getLogger(AcceptanceTest.class);

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Test
    public void 답변없음로그인유저같음() throws Exception {
        Question question = questionRepository.findById(4L).get();
        log.info("question : {}, answer : {}", question.getWriter(), question.getAnswers());
        question.delete(defaultUser());
    }

    @Test(expected = CannotDeleteException.class)
    public void 답변없음로그인유저다름() throws Exception {
        Question question = questionRepository.findById(4L).get();
        log.info("question : {}, answer : {}", question.getWriter(), question.getAnswers());
        User diffUser = findByUserId("jimmy");
        log.info("user : {}", diffUser.toString());
        question.delete(diffUser);
    }

    @Test
    public void 답변있음글쓴이같음() throws Exception {
        Question question = questionRepository.findById(5L).get();
        log.info("question : {}, answer : {}", question.getWriter(), question.getAnswers());
        question.delete(defaultUser());
    }

    @Test(expected = CannotDeleteException.class)
    public void 답변있음로그인유저다름() throws Exception {
        Question question = questionRepository.findById(5L).get();
        log.info("question : {}, answer : {}", question.getWriter(), question.getAnswers());
        User diffUser = findByUserId("jimmy");
        question.delete(diffUser);
    }

    @Test(expected = CannotDeleteException.class)
    public void 답변여러개로그인같음() throws Exception {
        Question question = questionRepository.findById(6L).get();
        log.info("question : {}, answer : {}", question.getWriter(), question.getAnswers());
        question.delete(defaultUser());
    }

    @Test(expected = CannotDeleteException.class)
    public void 답변여러개로그인다름() throws Exception {
        Question question = questionRepository.findById(6L).get();
        log.info("question : {}, answer : {}", question.getWriter(), question.getAnswers());
        User diffUser = findByUserId("jimmy");
        question.delete(diffUser);
    }
}
