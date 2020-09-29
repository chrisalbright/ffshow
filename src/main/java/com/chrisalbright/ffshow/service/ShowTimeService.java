package com.chrisalbright.ffshow.service;

import com.chrisalbright.ffshow.model.ShowTime;
import com.chrisalbright.ffshow.repository.ShowTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ShowTimeService {
  private final ShowTimeRepository repo;

  public Mono<ShowTime> addShowTime(ShowTime showTime) {
    return Mono.just(repo.save(showTime));
  }
}
