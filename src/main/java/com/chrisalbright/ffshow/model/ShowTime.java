package com.chrisalbright.ffshow.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalTime;

@Entity
@Data
@With
@NoArgsConstructor
@AllArgsConstructor
public class ShowTime {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @JsonBackReference(value = "movie")
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "MOVIE_ID")
  private Movie movie;

  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private LocalTime startTime;

  private BigDecimal ticketPrice;
}
