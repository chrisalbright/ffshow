package com.chrisalbright.ffshow.repository;

import com.chrisalbright.ffshow.model.ShowTime;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowTimeRepository extends CrudRepository<ShowTime, Integer> {
  Iterable<ShowTime> findAllByMovieId(Integer movieId);
}
