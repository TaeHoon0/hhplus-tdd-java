package io.hhplus.tdd.point.domain.entity;

import io.hhplus.tdd.point.domain.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PointHistory {

    private Long id;
    private Long userId;
    private Long amount;
    private TransactionType type;
    private Long updateMillis;

}
