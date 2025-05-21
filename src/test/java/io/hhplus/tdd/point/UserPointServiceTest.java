package io.hhplus.tdd.point;

import io.hhplus.tdd.point.domain.PointPolicy;
import io.hhplus.tdd.point.domain.entity.UserPoint;
import io.hhplus.tdd.point.domain.repository.UserPointRepository;
import io.hhplus.tdd.point.domain.service.UserPointService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserPointServiceTest {

    @Mock
    private PointPolicy pointPolicy;

    @Mock
    private UserPointRepository userPointRepository;

    @InjectMocks
    private UserPointService userPointService;

    @Test
    public void 충전_금액이_보유_한도를_초과하지_않을_때_성공() {

        // given
        long id = 1L;
        long amount = 100L;

        UserPoint before = new UserPoint(id, 100L, System.currentTimeMillis());
        UserPoint after = new UserPoint(id, 200L, System.currentTimeMillis());

        when(userPointRepository.selectById(id)).thenReturn(before);
        when(userPointRepository.insertOrUpdate(id, 200L)).thenReturn(after);

        // when
        UserPoint result = userPointService.charge(id, amount);

        // then
        verify(pointPolicy, Mockito.times(1)).validateMaxBalance(100L, 100L);
        verify(userPointRepository, Mockito.times(1)).selectById(id);
        verify(userPointRepository, Mockito.times(1)).insertOrUpdate(id, 200L);

        assertEquals(after, result);
    }

    @Test
    public void 충전_금액이_보유_한도를_초과하여_실패() {

        // given
        long id = 1L;
        long amount = 1_000_000L;

        UserPoint before = new UserPoint(id, 100L, System.currentTimeMillis());

        when(userPointRepository.selectById(id)).thenReturn(before);

        doThrow(new IllegalArgumentException("보유 포인트 한도를 초과합니다."))
                .when(pointPolicy).validateMaxBalance(before.getPoint(), amount);

        // when
        assertThrows(IllegalArgumentException.class, () -> {
            userPointService.charge(1L, amount);
        });

        // then
        verify(userPointRepository, never()).insertOrUpdate(id, amount);
    }

    @Test
    public void 사용_금액이_보유_포인트를_초과하지_않을_때_성공() {

        //given
        long id = 1L;
        long amount = 100L;

        UserPoint before = new UserPoint(id, 100L, System.currentTimeMillis());
        UserPoint after = new UserPoint(id, 0L, System.currentTimeMillis());

        when(userPointRepository.selectById(id)).thenReturn(before);
        when(userPointRepository.insertOrUpdate(id, 0L)).thenReturn(after);

        //when
        userPointService.use(id, amount);

        //then
        verify(pointPolicy, Mockito.times(1)).validateMinBalance(100L, 100L);
        verify(userPointRepository, Mockito.times(1)).selectById(id);
        verify(userPointRepository, Mockito.times(1)).insertOrUpdate(id, 0L);
    }

    @Test
    public void 사용_금액이_보유_포인트를_초과하여_실패() {

        //given
        long id = 1L;
        long amount = 1_000_000L;

        UserPoint before = new UserPoint(id, 100L, System.currentTimeMillis());

        when(userPointRepository.selectById(id)).thenReturn(before);

        doThrow(new IllegalArgumentException("보유 포인트가 사용하려는 포인트보다 적습니다."))
                .when(pointPolicy).validateMinBalance(before.getPoint(), amount);

        //when
        assertThrows(IllegalArgumentException.class, () -> {
            userPointService.use(id, amount);
        });

        //then
        verify(userPointRepository, never()).insertOrUpdate(id, amount);
    }
}
