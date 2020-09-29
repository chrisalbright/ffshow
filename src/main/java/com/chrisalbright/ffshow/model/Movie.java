package com.chrisalbright.ffshow.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@With
@NoArgsConstructor
@AllArgsConstructor
public class Movie implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer id;

  @JsonManagedReference(value = "movie")
  @OneToMany(fetch = FetchType.EAGER, mappedBy = "movie")
  List<ShowTime> showTimes;

  @JsonIgnore
  private String imdbId;

  @Transient
  private String title;

  @Transient
  Integer releaseYear;

  @Transient
  String rated;

  @Transient
  String description;

  @Transient
  String imdbRating;

}
