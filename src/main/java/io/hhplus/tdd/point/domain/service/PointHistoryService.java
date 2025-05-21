package io.hhplus.tdd.point.domain.service;

import io.hhplus.tdd.point.domain.entity.PointHistory;
import io.hhplus.tdd.point.domain.repository.PointHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointHistoryService {

    private final PointHistoryRepository pointHistoryRepository;

    public void saveHistory(PointHistory pointHistory) {

        pointHistoryRepository.insert(
                pointHistory.getUserId(), pointHistory.getAmount(), pointHistory.getType(), pointHistory.getUpdateMillis());
    }
}
