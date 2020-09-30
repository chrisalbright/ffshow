package com.chrisalbright.ffshow.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.Collections;

@Data
@With
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto {
  private String title;

  private Integer releaseYear;

  private String rated;

  private String description;

  private String imdbRating;

  private Iterable<ShowTime> showTimes = Collections.emptyList();

}
