package io.hhplus.tdd.point.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPointResponse {

    private Long id;
    private Long point;
    private Long updateMillis;
}
