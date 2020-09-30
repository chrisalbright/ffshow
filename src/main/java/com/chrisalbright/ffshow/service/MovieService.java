package com.chrisalbright.ffshow.service;

import com.chrisalbright.ffshow.config.OMDBConfiguration;
import com.chrisalbright.ffshow.model.Movie;
import com.chrisalbright.ffshow.model.MovieDto;
import com.chrisalbright.ffshow.model.OMDBMovieDetails;
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
  private final ShowTimeService showtimeService;
  private final WebClient omdbWebClient;
  private final OMDBConfiguration omdbConfiguration;

  private Mono<MovieDto> convertMovieEntityToDto(Movie movie) {
    return fetchOmdbMovie(omdbWebClient, omdbConfiguration, movie)
        .map(omdbMovie -> new MovieDto()
            .withTitle(omdbMovie.getTitle())
            .withDescription(omdbMovie.getPlot())
            .withImdbRating(omdbMovie.getImdbRating())
            .withReleaseYear(omdbMovie.getReleaseYear())
            .withRated(omdbMovie.getRated())
            .withShowTimes(showtimeService.showTimesForMovie(movie))
        );
  }

  private Mono<OMDBMovieDetails> fetchOmdbMovie(WebClient client, OMDBConfiguration conf, Movie movie) {
    return client.get()
        .uri("?apikey={apiKey}&i={movieId}", conf.getApiKey(), movie.getImdbId())
        .retrieve()
        .bodyToMono(OMDBMovieDetails.class);
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
