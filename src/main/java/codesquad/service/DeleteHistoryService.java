package codesquad.service;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import codesquad.domain.DeleteHistory;
import codesquad.domain.DeleteHistoryRepository;

@Service("deleteHistoryService")
public class DeleteHistoryService {
    public static final Logger logger = LoggerFactory.getLogger(DeleteHistoryService.class);

    @Resource(name = "deleteHistoryRepository")
    private DeleteHistoryRepository deleteHistoryRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveAll(List<DeleteHistory> deleteHistories) {
        for (DeleteHistory deleteHistory : deleteHistories) {
            logger.info("question 삭제 on delete service");
            deleteHistoryRepository.save(deleteHistory);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void save(DeleteHistory deleteHistory) {
        logger.info("answer 삭제 on delete service");
        deleteHistoryRepository.save(deleteHistory);
    }
}
