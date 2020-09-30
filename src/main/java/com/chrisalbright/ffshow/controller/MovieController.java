package com.chrisalbright.ffshow.controller;

import com.chrisalbright.ffshow.model.MovieDto;
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
  public Flux<MovieDto> getAllMovies() {
    return movieService.getAllMovies();
  }

  @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Returns a single movie, given an Id")
  public Mono<MovieDto> getMovieById(@PathVariable Integer id) {
    return movieService
        .getMovieDtoById(id)
        .switchIfEmpty(Mono.error(new MovieNotFoundException()));
  }

  @GetMapping(path = "/{movieId}/showtimes", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Returns all show times for a given movie")
  public Flux<ShowTime> getMovieShowTimes(@PathVariable Integer movieId) {
    return Flux
        .from(movieService.getMovieById(movieId))
        .switchIfEmpty(Mono.error(new MovieNotFoundException()))
        .flatMap(movie -> Flux.fromIterable(showTimeService.showTimesForMovie(movie)));
  }

  @PostMapping(path = "/{moveId}/showtimes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Adds a new show time for a given movie")
  public Mono<ShowTime> addNewShowtime(@PathVariable Integer moveId, @RequestBody ShowTime showTime) {

    showTime.setMovieId(moveId);

    return showTimeService
        .addShowTime(showTime)
        .onErrorMap(ex -> new MovieNotFoundException());
  }

}
