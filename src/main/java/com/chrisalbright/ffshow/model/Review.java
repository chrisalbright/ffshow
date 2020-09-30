package com.chrisalbright.ffshow.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@Entity
public class Review {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private Integer movieId;
  private String review;
  private String email;
  private String name;
  private Integer stars;

  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private LocalDate reviewedAt = LocalDate.now();
}
