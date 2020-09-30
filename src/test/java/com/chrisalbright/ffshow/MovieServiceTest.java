package com.chrisalbright.ffshow;

import com.chrisalbright.ffshow.config.OMDBConfiguration;
import com.chrisalbright.ffshow.http.OMDBClient;
import com.chrisalbright.ffshow.model.Movie;
import com.chrisalbright.ffshow.model.MovieDto;
import com.chrisalbright.ffshow.model.OMDBMovieDetails;
import com.chrisalbright.ffshow.repository.MovieRepository;
import com.chrisalbright.ffshow.service.MovieService;
import com.chrisalbright.ffshow.service.ReviewService;
import com.chrisalbright.ffshow.service.ShowTimeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collections;
import java.util.Optional;

@SpringBootTest
public class MovieServiceTest {

  @MockBean
  MovieRepository movieRepository;
  @MockBean
  ShowTimeService showTimeService;
  @MockBean
  ReviewService reviewService;
  @MockBean
  OMDBClient omdbClient;

  OMDBConfiguration omdbConfiguration = new OMDBConfiguration();

  @Autowired
  MovieService serviceUnderTest;

  @Test
  public void testGetMovieById() {

    // given
    Movie m = new Movie().withId(1).withImdbId("foobar");
    Mockito.when(movieRepository.findById(1)).thenReturn(Optional.of(m));
    //when
    Mono<Movie> foundMovie = serviceUnderTest.getMovieById(1);
    // then
    StepVerifier.create(foundMovie).expectNext(m).verifyComplete();

  }

  @Test
  public void testGetMovieDtoById() {
    // given
    Movie m = new Movie().withId(1).withImdbId("foobar");
    MovieDto movieDto = new MovieDto("Movie", 2000, "R", "A really good movie", "A+", Collections.emptyList(), Collections.emptyList());
    OMDBMovieDetails omdbMovie = new OMDBMovieDetails("Movie", 2000, "R", "A really good movie", "A+");
    Mockito.when(movieRepository.findById(1)).thenReturn(Optional.of(m));
    Mockito.when(omdbClient.fetchOmdbMovie(Mockito.any())).thenReturn(Mono.just(omdbMovie));
    //when
    Mono<MovieDto> foundMovie = serviceUnderTest.getMovieDtoById(1);
    // then
    StepVerifier.create(foundMovie).expectNext(movieDto).verifyComplete();

  }

}
