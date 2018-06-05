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
    public void 삭제성공() throws Exception {
        log.info("defaultUser : {}", defaultUser().toString());
        Question question = questionRepository.findById(1L).get();
        log.info("question : {}", question.getWriter().toString());
        assertThat(question.delete(defaultUser()), is(true));
    }


}
