package com.everis.parents.app.repository;

import com.everis.parents.app.bean.Parennt;
import java.util.Date;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * .
 * @author lriveras
 *
 */
public interface ParenRepository extends ReactiveMongoRepository<Parennt, String> {
  /**
 * findbynumberID.
 */
  Mono<Parennt> findByNumberID(String numberID);

  /**
 * findbyFullname.
 */
  Flux<Parennt> findByFullname(String fullname);
  
  /**
 * findBybirthdatebetween.
 */
  Flux<Parennt> findByBirthdateBetween(Date birthdate,Date birthdate1);

  /**
 * Solo devuelve un dcodumento.
 * obtenerfullname.
 */
  @Query("{ 'fullname': ?0 }")
  Mono<Parennt> findByFullname_par(String fullname);

  /**
   * Demo.
   */
  Flux<Parennt> findByIdFamily(String idFamily);
}
