package com.chrisalbright.ffshow.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Movie implements Serializable {

  @Id
  Integer id;

  @JsonManagedReference(value = "movie")
  @OneToMany(fetch = FetchType.EAGER, mappedBy = "movie")
  List<ShowTime> showTimes;

  @JsonIgnore
  private String imdbId;

  @With
  @Transient
  private String title;

  @With
  @Transient
  Integer releaseYear;

  @With
  @Transient
  String rating;

  @With
  @Transient
  String plot;

}
