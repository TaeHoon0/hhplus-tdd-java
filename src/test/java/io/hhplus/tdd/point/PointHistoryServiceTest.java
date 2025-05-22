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

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class PointHistoryServiceTest {

    @Mock
    private PointHistoryRepository pointHistoryRepository;

    @InjectMocks
    private PointHistoryService pointHistoryService;

    @Test
    public void 포인트_충전_이력_저장() {

        // given
        long id = 1L;
        long amount = 100L;
        TransactionType type = TransactionType.CHARGE;
        long updateMillis = System.currentTimeMillis();

        PointHistory pointHistory = PointHistory.builder()
                .userId(id)
                .amount(amount)
                .type(type)
                .updateMillis(updateMillis)
                .build();

        // when
        pointHistoryService.saveHistory(pointHistory);

        // then
        verify(pointHistoryRepository, times(1)).insert(id, amount, type, updateMillis);
    }

    @Test
    public void 포인트_사용_이력_저장() {

        // given
        long id = 1L;
        long amount = 100L;
        TransactionType type = TransactionType.USE;
        long updateMillis = System.currentTimeMillis();

        PointHistory pointHistory = PointHistory.builder()
                .userId(id)
                .amount(amount)
                .type(type)
                .updateMillis(updateMillis)
                .build();

        // when
        pointHistoryService.saveHistory(pointHistory);

        // then
        verify(pointHistoryRepository, times(1)).insert(id, amount, type, updateMillis);
    }

    @Test
    public void 포인트_이력이_있을_때_이력_조회() {

        // given
        long userId = 1L;
        List<PointHistory> histories = List.of(
                new PointHistory(1L, userId, 100L, TransactionType.CHARGE, System.currentTimeMillis()),
                new PointHistory(2L, userId, 50L, TransactionType.USE, System.currentTimeMillis())
        );

        when(pointHistoryRepository.selectAllByUserId(userId)).thenReturn(histories);

        // when
        pointHistoryService.getHistory(userId);

        // then
        assertThat(histories).hasSize(2);
        assertThat(histories.get(0).getAmount()).isEqualTo(100L);
        assertThat(histories.get(1).getType()).isEqualTo(TransactionType.USE);

        verify(pointHistoryRepository, times(1)).selectAllByUserId(userId);
    }

    @Test
    public void 포인트_이력이_없을_때_이력_조회() {

        //given
        long userId = 1L;
        List<PointHistory> histories = Collections.emptyList();

        when(pointHistoryRepository.selectAllByUserId(userId)).thenReturn(histories);

        //when
        pointHistoryService.getHistory(userId);

        //then
        assertThat(histories).isEmpty();
        verify(pointHistoryRepository, times(1)).selectAllByUserId(userId);
    }
}
