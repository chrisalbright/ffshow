package com.chrisalbright.ffshow.service;

import com.chrisalbright.ffshow.config.OMDBConfiguration;
import com.chrisalbright.ffshow.model.Movie;
import com.chrisalbright.ffshow.repository.MovieRepository;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MovieService {
  private final MovieRepository repo;
  private final WebClient omdbWebClient;
  private final OMDBConfiguration omdbConfiguration;

  @Getter
  @EqualsAndHashCode
  @RequiredArgsConstructor
  private static class Tuple {
    private final Movie movie;
    private final Mono<Movie> movieMono;
  }


  public Flux<Movie> getMovieDetails() {
    return Flux
        .fromIterable(repo.findAll())
        .map(movie ->
            new Tuple(movie,
                omdbWebClient
                    .get()
                    .uri("?apikey={apiKey}&i={movieId}", omdbConfiguration.getApiKey(), movie.getImdbId())
                    .retrieve()
                    .bodyToMono(Movie.class)
            )
        ).flatMap(tuple -> tuple
            .getMovieMono()
            .map(movie -> movie.withId(tuple.getMovie().getId())));
  }
}
