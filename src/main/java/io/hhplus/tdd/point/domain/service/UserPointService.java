package io.hhplus.tdd.point.domain.service;

import io.hhplus.tdd.point.domain.PointPolicy;
import io.hhplus.tdd.point.domain.entity.UserPoint;
import io.hhplus.tdd.point.domain.repository.UserPointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserPointService {

    private final PointPolicy pointPolicy;
    private final UserPointRepository userPointRepository;

    public UserPoint charge(long id, long amount) {

        UserPoint userPoint = userPointRepository.selectById(id);

        // 포인트 보유 한도 검증
        pointPolicy.validateMaxBalance(userPoint.getPoint(), amount);

        // 포인트 충전
        userPoint.charge(amount);

        return userPointRepository.insertOrUpdate(id, userPoint.getPoint());
    }

    public UserPoint use(long id, long amount) {

        UserPoint userPoint = userPointRepository.selectById(id);

        pointPolicy.validateMinBalance(userPoint.getPoint(), amount);

        userPoint.use(amount);

        return userPointRepository.insertOrUpdate(id, userPoint.getPoint());
    }

    public UserPoint get(long id) {

        return userPointRepository.selectById(id);
    }
}
