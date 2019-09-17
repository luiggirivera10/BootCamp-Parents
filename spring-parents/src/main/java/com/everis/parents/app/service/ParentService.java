package com.everis.parents.app.service;

import com.everis.parents.app.bean.Parennt;
import java.util.Date;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ParentService {

  public Flux<Parennt> findAll();

  public Mono<Parennt> findById(String id);

  public Mono<Parennt> save(Parennt parennt);

  public Mono<Void> delete(Parennt parennt);

  public Mono<Parennt> findByNumberID(String numberID);

  public Flux<Parennt> findByFullname(String fullname);

  public Flux<Parennt> findByBirthdateBetween(Date birthdate, Date birthdate1);

  public Mono<Parennt> obtenerPorName(String fullname);
}
