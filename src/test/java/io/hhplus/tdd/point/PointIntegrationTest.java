package io.hhplus.tdd.point;

import io.hhplus.tdd.point.application.usecase.PointUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PointIntegrationTest {

    @Autowired
    PointUseCase pointUseCase;

    @Test
    void 포인트를_충전하고_정상적으로_반환() {

        // given
        long userId = 1L;
        long amount = 1000L;

        // when
        var response = pointUseCase.chargePoint(userId, amount);

        // then
        assertThat(response.getId()).isEqualTo(userId);
        assertThat(response.getPoint()).isEqualTo(amount);
    }

    @Test
    void 포인트를_차감하고_정상적으로_반환() {

        // given
        long userId = 1L;
        pointUseCase.chargePoint(userId, 1500L);
        long useAmount = 500L;

        // when
        var response = pointUseCase.usePoint(userId, useAmount);

        // then
        assertThat(response.getId()).isEqualTo(userId);
        assertThat(response.getPoint()).isEqualTo(1000L); // 1500 - 500
    }

    @Test
    void 현재_포인트를_정상적으로_반환() {

        // given
        long userId = 1L;
        pointUseCase.chargePoint(userId, 2000L);
        pointUseCase.usePoint(userId, 300L);

        // when
        var response = pointUseCase.getPoint(userId);

        // then
        assertThat(response.getPoint()).isEqualTo(1700L);
    }

    @Test
    void 포인트_이력을_반환() {

        // given
        long userId = 1L;
        pointUseCase.chargePoint(userId, 1000L);
        pointUseCase.usePoint(userId, 300L);

        // when
        var histories = pointUseCase.getPointHistory(userId);

        // then
        assertThat(histories).hasSize(2);
        assertThat(histories.get(0).getType().name()).isEqualTo("CHARGE");
        assertThat(histories.get(1).getType().name()).isEqualTo("USE");
    }
}
