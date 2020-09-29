package com.chrisalbright.ffshow.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OMDBMovieDetails {
  @JsonAlias("Title")
  String title;

  @JsonAlias("Year")
  Integer releaseYear;

  @JsonAlias("Rated")
  String rating;

  @JsonAlias("Plot")
  String plot;

}
