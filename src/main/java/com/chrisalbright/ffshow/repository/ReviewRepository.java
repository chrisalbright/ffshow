package com.chrisalbright.ffshow.repository;

import com.chrisalbright.ffshow.model.Review;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<Review, Integer> {
  Iterable<Review> findAllByMovieId(Integer id);
}
