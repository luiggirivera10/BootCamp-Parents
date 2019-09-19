package com.everis.parents.app.service;

import com.everis.parents.app.bean.Parennt;
import java.util.Date;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * .
 * @author lriveras
 *
 */
public interface ParentService {
  /**
 * findAll.
 */
  public Flux<Parennt> findAll();

  /**
 * findById.
 */
  public Mono<Parennt> findById(String id);

  /**
 * save.
 */
  public Mono<Parennt> save(Parennt parennt);

  /**
 * delete.
 */
  public Mono<Void> delete(Parennt parennt);

  /**
 * findByNumberID.
 */
  public Mono<Parennt> findByNumberID(String numberID);

  /**
 * findByFullname.
 */
  public Flux<Parennt> findByFullname(String fullname);

  /**
 * findByBirthdateBetween.
 */
  public Flux<Parennt> findByBirthdateBetween(Date birthdate, Date birthdate1);

  /**
 * Solo para TEST.
 * obtenerPorName.
 */
  public Mono<Parennt> findByFullname_par(String fullname);
}
