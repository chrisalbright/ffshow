package com.chrisalbright.ffshow.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Movie implements Serializable {

  @Id
  @With
  Integer id;

  @JsonIgnore
  private String imdbId;

  @Transient
  @JsonAlias("Title")
  private String title;

  @Transient
  @JsonAlias("Year")
  Integer releaseYear;

  @Transient
  @JsonAlias("Type")
  String type;

  @Transient
  @JsonAlias("Rated")
  String rating;

  @Transient
  @JsonAlias("Plot")
  String plot;

}
