package com.chrisalbright.ffshow.controller;

import com.chrisalbright.ffshow.model.Movie;
import com.chrisalbright.ffshow.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MovieController {
  private final MovieService movieService;


  @GetMapping
  public Flux<Movie> index() {
    return movieService.getMovieDetails();
  }

}
