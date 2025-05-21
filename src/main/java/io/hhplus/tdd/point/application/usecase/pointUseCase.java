package io.hhplus.tdd.point.application.usecase;

import io.hhplus.tdd.point.application.mapper.PointMapper;
import io.hhplus.tdd.point.domain.TransactionType;
import io.hhplus.tdd.point.domain.entity.UserPoint;
import io.hhplus.tdd.point.domain.service.PointHistoryService;
import io.hhplus.tdd.point.domain.service.UserPointService;
import io.hhplus.tdd.point.presentation.dto.response.UserPointResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class pointUseCase {

    private final UserPointService userPointService;
    private final PointHistoryService pointHistoryService;

    /**
     * 사용자의 포인트 충전
     */
    public UserPointResponse chargePoint(long id, long amount) {

        // 포인트 충전
        UserPoint userPoint = userPointService.charge(id, amount);

        // 이력 저장
        pointHistoryService.saveHistory(PointMapper.toEntity(
                userPoint.getId(), amount, TransactionType.CHARGE, userPoint.getUpdateMillis()));

        return PointMapper.toDto(userPoint);
    }

    /**
     * 사용자의 포인트 사용
     */
//    public UserPointResponse usePoint(long id, long amount) {
//
//        return PointMapper.toDto(userPointService.use(id, amount));
//    }
}
