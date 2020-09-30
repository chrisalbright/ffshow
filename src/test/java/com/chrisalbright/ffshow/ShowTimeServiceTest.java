package com.chrisalbright.ffshow;

import com.chrisalbright.ffshow.model.Review;
import com.chrisalbright.ffshow.model.ShowTime;
import com.chrisalbright.ffshow.repository.ReviewRepository;
import com.chrisalbright.ffshow.repository.ShowTimeRepository;
import com.chrisalbright.ffshow.service.ReviewService;
import com.chrisalbright.ffshow.service.ShowTimeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
public class ShowTimeServiceTest {

  @MockBean
  ShowTimeRepository fakeRepository;

  @Autowired
  ShowTimeService serviceUnderTest;

  @Test
  public void addShowTimeSuccess() {
    final ShowTime showTime = new ShowTime();

    Mockito.when(fakeRepository.save(showTime)).thenReturn(showTime);

    // when
    final Mono<ShowTime> showTimeMono = serviceUnderTest.addShowTime(showTime);

    // then
    StepVerifier
        .create(showTimeMono)
        .expectNext(showTime)
        .verifyComplete();

  }

  @Test
  public void addShowTimeFailure() {
    Mockito.when(fakeRepository.save(Mockito.any(ShowTime.class))).thenThrow(new DataIntegrityViolationException(""));

    // when
    final Mono<ShowTime> showTimeMono = serviceUnderTest.addShowTime(new ShowTime());

    // then
    StepVerifier.create(showTimeMono).expectError(DataIntegrityViolationException.class).verify();
  }
}
