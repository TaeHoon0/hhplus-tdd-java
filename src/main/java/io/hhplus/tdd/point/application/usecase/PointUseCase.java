package io.hhplus.tdd.point.application.usecase;

import io.hhplus.tdd.point.application.mapper.PointMapper;
import io.hhplus.tdd.point.domain.TransactionType;
import io.hhplus.tdd.point.domain.entity.PointHistory;
import io.hhplus.tdd.point.domain.entity.UserPoint;
import io.hhplus.tdd.point.domain.service.PointHistoryService;
import io.hhplus.tdd.point.domain.service.UserPointService;
import io.hhplus.tdd.point.infrastructure.lock.LockManager;
import io.hhplus.tdd.point.presentation.dto.response.PointHistoryResponse;
import io.hhplus.tdd.point.presentation.dto.response.UserPointResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PointUseCase {

    private final UserPointService userPointService;
    private final PointHistoryService pointHistoryService;
    private final LockManager lockManager;

    /**
     * 사용자의 포인트 충전
     */
    public UserPointResponse chargePoint(long id, long amount) {

        UserPoint userPoint;


        try {
            // 락 획득 시도
            lockManager.getLock(id);
            // 포인트 충전
            userPoint = userPointService.charge(id, amount);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lockManager.releaseLock(id);
        }

        // 이력 저장
        pointHistoryService.saveHistory(
                PointMapper.toEntity(userPoint.getId(), amount, TransactionType.CHARGE, userPoint.getUpdateMillis()));

        return PointMapper.toDto(userPoint);
    }

    /**
     * 사용자의 포인트 사용
     */
    public UserPointResponse usePoint(long id, long amount) {

        // 포인트 사용
        UserPoint userPoint = userPointService.use(id, amount);

        // 이력 저장
        pointHistoryService.saveHistory(
                PointMapper.toEntity(userPoint.getId(), amount, TransactionType.USE, userPoint.getUpdateMillis())
        );

        return PointMapper.toDto(userPoint);
    }

    /**
     * 사용자의 포인트 조회
     */
    public UserPointResponse getPoint(long id) {

        // 포인트 조회
        UserPoint userPoint = userPointService.get(id);

        return PointMapper.toDto(userPoint);
    }

    /**
     * 사용자의 포인트의 이력 조회
     */
    public List<PointHistoryResponse> getPointHistory(long id) {

        List<PointHistory> pointHistories = pointHistoryService.getHistory(id);

        return pointHistories.stream()
                .map(PointMapper::toDto)
                .toList();
    }
}
