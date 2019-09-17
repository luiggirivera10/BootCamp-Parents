package com.everis.parents.app.service.impl;

import com.everis.parents.app.bean.Parennt;
import com.everis.parents.app.repository.ParenRepository;
import com.everis.parents.app.service.ParentService;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ParentServiceImpl implements ParentService {
 
  @Autowired
 private ParenRepository parenRepository;
 
  @Override
 public Flux<Parennt> findAll() {
    return parenRepository.findAll();
  }

  @Override
 public Mono<Parennt> findById(String id) {
    return parenRepository.findById(id);
  }

  @Override
 public Mono<Parennt> save(Parennt parennt) {
    return parenRepository.save(parennt);
  }

  @Override
 public Mono<Void> delete(Parennt parennt) {
    return parenRepository.delete(parennt);
  }


  @Override
 public Mono<Parennt> findByNumberID(String numberID) {
    return parenRepository.findByNumberID(numberID);
  }

  @Override
 public Flux<Parennt> findByFullname(String fullname) {
    return parenRepository.findByFullname(fullname);
  }

  @Override
public Flux<Parennt> findByBirthdateBetween(Date birthdate, Date birthdate1) {
    return parenRepository.findByBirthdateBetween(birthdate, birthdate1);
  }


}
