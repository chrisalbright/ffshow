package com.chrisalbright.ffshow.service;

import com.chrisalbright.ffshow.http.OMDBClient;
import com.chrisalbright.ffshow.model.Movie;
import com.chrisalbright.ffshow.model.MovieDto;
import com.chrisalbright.ffshow.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MovieService {
  private final MovieRepository repo;
  private final ShowTimeService showtimeService;
  private final ReviewService reviewService;
  private final OMDBClient omdbClient;

  private Mono<MovieDto> convertMovieEntityToDto(Movie movie) {
    return omdbClient.fetchOmdbMovie(movie)
        .map(omdbMovie -> new MovieDto()
            .withTitle(omdbMovie.getTitle())
            .withDescription(omdbMovie.getPlot())
            .withImdbRating(omdbMovie.getImdbRating())
            .withReleaseYear(omdbMovie.getReleaseYear())
            .withRated(omdbMovie.getRated())
            .withShowTimes(showtimeService.showTimesForMovie(movie))
            .withReviews(reviewService.reviewsForMovie(movie))
        );
  }


  public Mono<Movie> getMovieById(Integer id) {
    return Mono
        .justOrEmpty(repo.findById(id));
  }

  public Mono<MovieDto> getMovieDtoById(Integer id) {
    return getMovieById(id)
        .flatMap(this::convertMovieEntityToDto);
  }

  public Flux<MovieDto> getAllMovies() {
    return Flux
        .fromIterable(repo.findAll())
        .flatMap(this::convertMovieEntityToDto);
  }
}
