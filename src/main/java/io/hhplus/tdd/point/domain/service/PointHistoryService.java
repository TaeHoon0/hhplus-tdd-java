package io.hhplus.tdd.point.domain.service;

import io.hhplus.tdd.point.domain.entity.PointHistory;
import io.hhplus.tdd.point.domain.repository.PointHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PointHistoryService {

    private final PointHistoryRepository pointHistoryRepository;

    public void saveHistory(PointHistory pointHistory) {

        pointHistoryRepository.insert(
                pointHistory.getUserId(), pointHistory.getAmount(), pointHistory.getType(), pointHistory.getUpdateMillis());
    }

    public List<PointHistory> getHistory(long id) {

        return pointHistoryRepository.selectAllByUserId(id);
    }
}
