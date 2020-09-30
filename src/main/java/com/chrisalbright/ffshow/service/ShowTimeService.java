package com.chrisalbright.ffshow.service;

import com.chrisalbright.ffshow.model.Movie;
import com.chrisalbright.ffshow.model.ShowTime;
import com.chrisalbright.ffshow.repository.ShowTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ShowTimeService {
  private final ShowTimeRepository repo;

  public Mono<ShowTime> addShowTime(ShowTime showTime) {
    Mono<ShowTime> showTimeMono;
    try {
      showTimeMono = Mono.just(repo.save(showTime));
    } catch (DataIntegrityViolationException ex) {
      showTimeMono = Mono.error(ex);
    }
    return showTimeMono;
  }

  public Iterable<ShowTime> showTimesForMovie(Movie movie) {
    return repo.findAllByMovieId(movie.getId());
  }
}
