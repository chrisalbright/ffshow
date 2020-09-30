package com.chrisalbright.ffshow;

import com.chrisalbright.ffshow.model.Review;
import com.chrisalbright.ffshow.repository.ReviewRepository;
import com.chrisalbright.ffshow.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
public class ReviewServiceTest {

  @MockBean
  ReviewRepository fakeRepository;

  @Autowired
  ReviewService serviceUnderTest;

  @Test
  public void addReviewSuccess() {
    final Review review = new Review();

    Mockito.when(fakeRepository.save(review)).thenReturn(review);

    // when
    final Mono<Review> reviewMono = serviceUnderTest.addReview(review);

    // then
    StepVerifier
        .create(reviewMono)
        .expectNext(review)
        .verifyComplete();

  }

  @Test
  public void addReviewFailure() {
    Mockito.when(fakeRepository.save(Mockito.any(Review.class))).thenThrow(new DataIntegrityViolationException(""));

    // when
    final Mono<Review> reviewMono = serviceUnderTest.addReview(new Review());

    // then
    StepVerifier.create(reviewMono).expectError(DataIntegrityViolationException.class).verify();
  }
}
