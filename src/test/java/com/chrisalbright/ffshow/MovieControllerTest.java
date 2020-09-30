package com.chrisalbright.ffshow;

import com.chrisalbright.ffshow.config.SecurityConfiguration;
import com.chrisalbright.ffshow.controller.MovieController;
import com.chrisalbright.ffshow.model.MovieDto;
import com.chrisalbright.ffshow.model.ShowTime;
import com.chrisalbright.ffshow.service.MovieService;
import com.chrisalbright.ffshow.service.ReviewService;
import com.chrisalbright.ffshow.service.ShowTimeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = MovieController.class)
public class MovieControllerTest {

  @MockBean
  private MovieService movieService;
  @MockBean
  private ShowTimeService showTimeService;
  @MockBean
  private ReviewService reviewService;
  @MockBean
  private SecurityConfiguration securityConfiguration;

  @Autowired
  WebTestClient webTestClient;

  @Test
  public void testGetMovieById() {
    final MovieDto movieDto = new MovieDto().withTitle("foo");
    when(movieService.getMovieDtoById(1)).thenReturn(Mono.just(movieDto));
    webTestClient
        .get()
        .uri("/movies/{id}", 1)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBody(MovieDto.class)
        .isEqualTo(movieDto);
  }

  @Test
  public void testGetMovieByIdWithMissingMovie() {
    when(movieService.getMovieDtoById(1)).thenReturn(Mono.empty());
    webTestClient
        .get()
        .uri("/movies/{id}", 1)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isNotFound();
  }

  @Test
  public void testAddShowTimeWorksWithCorrectApiKey() {

    final ShowTime showTime =
        new ShowTime()
            .withStartTime(LocalTime.NOON)
            .withTicketPrice(BigDecimal.TEN);

    when(securityConfiguration.getApiKey()).thenReturn("api-key");
    when(showTimeService.addShowTime(any())).thenReturn(Mono.just(showTime.withId(1)));

    webTestClient
        .post()
        .uri("/movies/{id}/showtimes?apiKey={apiKey}", 1, "api-key")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(showTime)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBody(ShowTime.class).isEqualTo(showTime.withId(1));
  }

  @Test
  public void testAddShowTimeFailsWithInCorrectApiKey() {

    final ShowTime showTime =
        new ShowTime()
            .withStartTime(LocalTime.NOON)
            .withTicketPrice(BigDecimal.TEN);

    when(securityConfiguration.getApiKey()).thenReturn("api-key");
    when(showTimeService.addShowTime(any())).thenReturn(Mono.just(showTime.withId(1)));

    webTestClient
        .post()
        .uri("/movies/{id}/showtimes?apiKey={apiKey}", 1, "wrong-api-key")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(showTime)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isForbidden();
  }
}
