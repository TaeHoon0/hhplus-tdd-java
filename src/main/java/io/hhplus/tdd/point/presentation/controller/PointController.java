package io.hhplus.tdd.point.presentation.controller;

import io.hhplus.tdd.point.domain.entity.PointHistory;
import io.hhplus.tdd.point.presentation.dto.request.UserPointRequest;
import io.hhplus.tdd.point.application.usecase.PointUseCase;
import io.hhplus.tdd.point.presentation.dto.response.UserPointResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/point")
@RequiredArgsConstructor
public class PointController {

    private final PointUseCase pointUseCase;
    private static final Logger log = LoggerFactory.getLogger(PointController.class);

    /**
     * TODO - 특정 유저의 포인트를 조회하는 기능을 작성해주세요.
     */
    @GetMapping("{id}")
    public ResponseEntity<UserPointResponse> point(
            @PathVariable @Min(1) long id
    ) {

        UserPointResponse response = pointUseCase.getPoint(id);

        return ResponseEntity.ok(response);
    }

    /**
     * TODO - 특정 유저의 포인트 충전/이용 내역을 조회하는 기능을 작성해주세요.
     */
    @GetMapping("{id}/histories")
    public List<PointHistory> history(
            @PathVariable @Min(1) long id
    ) {
        return List.of();
    }

    /**
     * TODO - 특정 유저의 포인트를 충전하는 기능을 작성해주세요.
     */
    @PatchMapping("{id}/charge")
    public ResponseEntity<UserPointResponse> charge(
            @PathVariable @Min(1) long id,
            @RequestBody @Valid UserPointRequest request
    ) {

        UserPointResponse response = pointUseCase.chargePoint(id, request.getAmount());

        return ResponseEntity.ok(response);
    }

    /**
     * TODO - 특정 유저의 포인트를 사용하는 기능을 작성해주세요.
     */
    @PatchMapping("{id}/use")
    public ResponseEntity<UserPointResponse> use(
            @PathVariable @Min(1) long id,
            @RequestBody @Valid UserPointRequest request
    ) {

        UserPointResponse response = pointUseCase.usePoint(id, request.getAmount());

        return ResponseEntity.ok(response);
    }
}
