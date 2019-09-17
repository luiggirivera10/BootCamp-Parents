package com.everis.parents.app.repository;

import com.everis.parents.app.bean.Parennt;
import java.util.Date;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface ParenRepository extends ReactiveMongoRepository<Parennt, String> {

  Mono<Parennt> findByNumberID(String numberID);

  Flux<Parennt> findByFullname(String fullname);

  Flux<Parennt> findByBirthdateBetween(Date birthdate,Date birthdate1);

  @Query("{ 'fullname': ?0 }")
  public Mono<Parennt> obtenerPorName(String fullname);
}
