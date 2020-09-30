package com.chrisalbright.ffshow.service;

import com.chrisalbright.ffshow.model.Movie;
import com.chrisalbright.ffshow.model.Review;
import com.chrisalbright.ffshow.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ReviewService {

  private final ReviewRepository repo;

  public Iterable<Review> reviewsForMovie(Movie movie) {
    return repo.findAllByMovieId(movie.getId());
  }

  public Mono<Review> addReview(Review review) {
    Mono<Review> reviewMono;
    try {
      reviewMono = Mono.just(repo.save(review));
    } catch (DataIntegrityViolationException ex){
      reviewMono = Mono.error(ex);
    }
    return reviewMono;
  }
}
