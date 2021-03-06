package com.chrisalbright.ffshow.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OMDBMovieDetails {
  @JsonAlias("Title")
  String title;

  @JsonAlias("Year")
  Integer releaseYear;

  @JsonAlias("Rated")
  String rated;

  @JsonAlias("Plot")
  String plot;

  @JsonAlias("imdbRating")
  String imdbRating;

}
