package com.chrisalbright.ffshow.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ShowTime {
  @Id
  private Integer id;

  @JsonBackReference(value = "movie")
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "MOVIE_ID")
  private Movie movie;

  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private LocalTime startTime;

  private BigDecimal ticketPrice;
}
