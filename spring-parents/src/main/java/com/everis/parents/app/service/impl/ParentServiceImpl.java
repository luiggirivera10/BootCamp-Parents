package com.everis.parents.app.service.impl;

import com.everis.parents.app.bean.Parennt;
import com.everis.parents.app.repository.ParenRepository;
import com.everis.parents.app.service.ParentService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
  
/**
  * .
 * @author lriveras.
 *
 */
@Service
public class ParentServiceImpl implements ParentService {

  /**
 * Injection. 
 */
  @Autowired
 private ParenRepository parenRepository;

  /**
 * findAll.
 */
  @Override
 public Flux<Parennt> findAll() {
    return parenRepository.findAll();
  }

  /**
 * findById.
 */
  @Override
 public Mono<Parennt> findById(String id) {
    return parenRepository.findById(id);
  }

  /**
 * save.
 */
  @Override
 public Mono<Parennt> save(Parennt parennt) {
    return parenRepository.save(parennt);
  }

  /**
 * delete.
 */
  @Override
 public Mono<Void> delete(Parennt parennt) {
    return parenRepository.delete(parennt);
  }

  /**
 * findByNumberID.
 */
  @Override
 public Mono<Parennt> findByNumberID(String numberID) {
    return parenRepository.findByNumberID(numberID);
  }

  /**
 * findByFullname.
 */
  @Override
 public Flux<Parennt> findByFullname(String fullname) {
    return parenRepository.findByFullname(fullname);
  }

  /**
 * findByBirthdateBetween.
 */
  @Override
public Flux<Parennt> findByBirthdateBetween(Date birthdate, Date birthdate1) {
    return parenRepository.findByBirthdateBetween(birthdate, birthdate1);
  }

  /**
 * Solo para prueba.
 * obtenerPorName.
 */
  @Override
  public Mono<Parennt> findByFullname_par(String fullname) {
    return parenRepository.findByFullname_par(fullname);
  }

  @Override
  public Flux<Parennt> findByIdFamily(String idFamily) {
    return parenRepository.findByIdFamily(idFamily);
  }

}
