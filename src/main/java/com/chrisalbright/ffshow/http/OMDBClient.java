package com.chrisalbright.ffshow.http;

import com.chrisalbright.ffshow.config.OMDBConfiguration;
import com.chrisalbright.ffshow.model.Movie;
import com.chrisalbright.ffshow.model.OMDBMovieDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class OMDBClient {

  private final WebClient webClient;
  private final OMDBConfiguration omdbConfiguration;

  public Mono<OMDBMovieDetails> fetchOmdbMovie(Movie movie) {
    return webClient.get()
        .uri("?apikey={apiKey}&i={movieId}", omdbConfiguration.getApiKey(), movie.getImdbId())
        .retrieve()
        .bodyToMono(OMDBMovieDetails.class);
  }

}
