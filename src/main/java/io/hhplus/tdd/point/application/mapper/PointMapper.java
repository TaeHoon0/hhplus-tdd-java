package io.hhplus.tdd.point.application.mapper;

import io.hhplus.tdd.point.domain.TransactionType;
import io.hhplus.tdd.point.domain.entity.PointHistory;
import io.hhplus.tdd.point.domain.entity.UserPoint;
import io.hhplus.tdd.point.presentation.dto.response.PointHistoryResponse;
import io.hhplus.tdd.point.presentation.dto.response.UserPointResponse;

public class PointMapper {

    public static UserPointResponse toDto(
        UserPoint entity
    ) {

        return UserPointResponse.builder()
                .id(entity.getId())
                .point(entity.getPoint())
                .updateMillis(entity.getUpdateMillis())
                .build();
    }

    public static PointHistory toEntity(
            long userId, long amount, TransactionType type, long updateMillis
    ) {

        return PointHistory.builder()
                .userId(userId)
                .amount(amount)
                .type(type)
                .updateMillis(updateMillis)
                .build();
    }

    public static PointHistoryResponse toDto(PointHistory entity) {

        return PointHistoryResponse.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .amount(entity.getAmount())
                .type(entity.getType())
                .updateMillis(entity.getUpdateMillis())
                .build();
    }

}
