package io.hhplus.tdd.point;

import io.hhplus.tdd.point.domain.TransactionType;
import io.hhplus.tdd.point.domain.entity.PointHistory;
import io.hhplus.tdd.point.domain.repository.PointHistoryRepository;
import io.hhplus.tdd.point.domain.service.PointHistoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class PointHistoryServiceTest {

    @Mock
    private PointHistoryRepository pointHistoryRepository;

    @InjectMocks
    private PointHistoryService pointHistoryService;

    @Test
    public void 포인트_충전_이력_저장() {

        // given
        long userId = 1L;
        long amount = 100L;
        TransactionType type = TransactionType.CHARGE;
        long updateMillis = System.currentTimeMillis();

        PointHistory pointHistory = PointHistory.builder()
                .userId(userId)
                .amount(amount)
                .type(type)
                .updateMillis(updateMillis)
                .build();

        // when
        pointHistoryService.saveHistory(pointHistory);

        // then
        verify(pointHistoryRepository, times(1)).insert(userId, amount, type, updateMillis);
    }

    @Test
    public void 포인트_사용_이력_저장() {

        // given
        long userId = 1L;
        long amount = 100L;
        TransactionType type = TransactionType.USE;
        long updateMillis = System.currentTimeMillis();

        PointHistory pointHistory = PointHistory.builder()
                .userId(userId)
                .amount(amount)
                .type(type)
                .updateMillis(updateMillis)
                .build();

        // when
        pointHistoryService.saveHistory(pointHistory);

        // then
        verify(pointHistoryRepository, times(1)).insert(userId, amount, type, updateMillis);
    }
}
