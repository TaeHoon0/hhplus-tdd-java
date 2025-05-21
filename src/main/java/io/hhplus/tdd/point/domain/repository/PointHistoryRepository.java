package io.hhplus.tdd.point.domain.repository;

import io.hhplus.tdd.point.domain.TransactionType;
import io.hhplus.tdd.point.domain.entity.PointHistory;

import java.util.List;

public interface PointHistoryRepository {

    PointHistory insert(long userId, long amount, TransactionType type, long updateMillis);

    List<PointHistory> selectAllByUserId(long userId);
}
