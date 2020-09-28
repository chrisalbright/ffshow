package com.chrisalbright.ffshow.controller;

import com.chrisalbright.ffshow.model.Movie;
import com.chrisalbright.ffshow.service.MovieService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/movies")
public class MovieController {
  private final MovieService movieService;

  @GetMapping
  @ApiOperation(value = "Returns all movies")
  public Flux<Movie> getAllMovies() {
    return movieService.getAllMovies();
  }

  @GetMapping("/{id}")
  @ApiOperation(value = "Returns a single movie, given an Id")
  public Mono<Movie> getMovieById(@PathVariable Integer id) {
    return movieService.getMovieById(id);
  }

}
