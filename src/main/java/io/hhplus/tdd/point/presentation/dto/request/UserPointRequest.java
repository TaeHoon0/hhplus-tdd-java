package io.hhplus.tdd.point.presentation.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class UserPointRequest {

    @NotNull
    @Min(value = 1, message = "포인트 충전, 사용의 최소 단위은 1입니다.")
    private Long amount;
}
