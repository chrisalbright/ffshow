package com.chrisalbright.ffshow.controller;

import com.chrisalbright.ffshow.model.Movie;
import com.chrisalbright.ffshow.model.ShowTime;
import com.chrisalbright.ffshow.service.MovieService;
import com.chrisalbright.ffshow.service.ShowTimeService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/movies")
public class MovieController {
  private final MovieService movieService;
  private final ShowTimeService showTimeService;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Returns all movies")
  public Flux<Movie> getAllMovies() {
    return movieService.getAllMovies();
  }

  @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Returns a single movie, given an Id")
  public Mono<Movie> getMovieById(@PathVariable Integer id) {
    return movieService
        .getMovieById(id)
        .switchIfEmpty(Mono.error(new MovieNotFoundException()));
  }

  @GetMapping(path = "/{id}/showtimes", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Returns all show times for a given movie")
  public Flux<ShowTime> getMovieShowTimes(@PathVariable Integer id) {
    return Flux
        .from(movieService.getMovieById(id))
        .switchIfEmpty(Mono.error(new MovieNotFoundException()))
        .flatMap(movie -> Flux.fromIterable(movie.getShowTimes()));
  }

  @PostMapping(path = "/{id}/showtimes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Adds a new show time for a given movie")
  public Mono<ShowTime> addNewShowtime(@PathVariable Integer id, @RequestBody ShowTime showTime) {
    return
        movieService
        .getMovieById(id)
            .switchIfEmpty(Mono.error(new MovieNotFoundException()))
            .map(showTime::withMovie)
            .flatMap(showTimeService::addShowTime);
  }

}
