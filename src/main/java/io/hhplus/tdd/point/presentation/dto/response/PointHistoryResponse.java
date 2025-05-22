package io.hhplus.tdd.point.presentation.dto.response;

import io.hhplus.tdd.point.domain.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointHistoryResponse {

    private Long id;
    private Long userId;
    private Long amount;
    private TransactionType type;
    private Long updateMillis;
}
