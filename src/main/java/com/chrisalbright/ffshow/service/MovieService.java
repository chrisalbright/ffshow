package com.chrisalbright.ffshow.service;

import com.chrisalbright.ffshow.config.OMDBConfiguration;
import com.chrisalbright.ffshow.model.Movie;
import com.chrisalbright.ffshow.repository.MovieRepository;
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

  private static Mono<Movie> hydrateMovie(WebClient client, OMDBConfiguration conf, Movie movie) {
    return client.get()
        .uri("?apikey={apiKey}&i={movieId}", conf.getApiKey(), movie.getImdbId())
        .retrieve()
        .bodyToMono(Movie.class)
        .map(movieDetails -> movieDetails.withId(movie.getId()));

  }

  public Mono<Movie> getMovieById(Integer id) {
    return Mono
        .justOrEmpty(repo.findById(id))
        .flatMap(movie -> hydrateMovie(omdbWebClient, omdbConfiguration, movie));
  }

  public Flux<Movie> getAllMovies() {
    return Flux
        .fromIterable(repo.findAll())
        .flatMap(movie -> hydrateMovie(omdbWebClient, omdbConfiguration, movie));
  }
}
