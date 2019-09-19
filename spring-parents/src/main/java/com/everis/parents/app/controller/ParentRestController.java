package com.everis.parents.app.controller;

import com.everis.parents.app.bean.Parennt;
import com.everis.parents.app.service.ParentService;
import java.util.Date;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * .
 * @author lriveras.
 *
 */
@RestController
@RequestMapping("/api/v1.0")
public class ParentRestController {
  /**
 * Injec ParentService.
 */
  @Autowired
 private ParentService repos;
  /**
 * .
 */
  private static final Logger log = LoggerFactory.getLogger(ParentRestController.class);
  
  /**
   * .
   * */
  @GetMapping("/parents")
 public Flux<Parennt> findAll() {
    final Flux<Parennt> parennts = repos.findAll().map(parent -> {
      parent.setFullname(parent.getFullname().toUpperCase());
      return parent;
    }).doOnNext(stu -> log.info(stu.getFullname()));
    return parennts;
  }
  
  /**
   * .
   * */ 
  @GetMapping("/parents/{id}")
 public Mono<Parennt> findById(@PathVariable String id) {
    final Flux<Parennt> parennts = repos.findAll();
    final Mono<Parennt> parennt = parennts.filter(s -> s.getId().equals(id)).next()
        .doOnNext(stu -> log.info(stu.getFullname()));
    return parennt;
  }

  /**
   * .
   * */
  @PostMapping("/parents")
 public Mono<ResponseEntity<Parennt>> newParent(@Valid @RequestBody final Parennt parennt) {
    return repos.save(parennt)
        .map(newParent -> new ResponseEntity<>(newParent, HttpStatus.CREATED))
    .defaultIfEmpty(new ResponseEntity<>(HttpStatus.CONFLICT));
  }
  /**
   * .
   * */
  @PutMapping("/parents/{id}")
 public Mono<ResponseEntity<Parennt>> updateParent(@PathVariable(value = "id") final String id,
      @Valid @RequestBody final Parennt parennt) {
    return repos.findById(id).flatMap(existingParent -> {
      existingParent.setFullname(parennt.getFullname());
      existingParent.setGender(parennt.getGender());
      existingParent.setBirthdate(parennt.getBirthdate());
      existingParent.setTypeID(parennt.getTypeID());
      existingParent.setNumberID(parennt.getNumberID());
      existingParent.setIdFamily(parennt.getIdFamily());
      return repos.save(existingParent);
    }).map(updatedParent -> new ResponseEntity<>(updatedParent, HttpStatus.CREATED))
    .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  /**
   * .
   * */
  @DeleteMapping("/parents/{id}")
 public Mono<ResponseEntity<Void>> deleteParent(@PathVariable(value = "id") final String id) {

    return repos.findById(id)
    .flatMap(existingParent -> repos.delete(existingParent)
      .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
    .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  /**
 * Servicio findByNumberID.
 */
  @GetMapping("/parents/doc/{numberID}")
 public Mono<ResponseEntity<Parennt>> 
      findByNumberID(@PathVariable("numberID") final String numberID) {
    return repos.findByNumberID(numberID)
        .map(newParent -> new ResponseEntity<>(newParent, HttpStatus.FOUND))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  /**
  * Servicio findByName.
 */
  @GetMapping("/parents/fname/{fullname}")
  public Flux<Parennt>
      findByName(@PathVariable(value = "fullname") final String fullname) {
    return repos.findByFullname(fullname);
  }

  /**
   * .
   */
  @GetMapping("/parents/nombre/{fullname}")
  public Mono<ResponseEntity<Parennt>> findByFullnamex(@PathVariable ("fullname") final String fullname) {
    return repos.findByFullname_par(fullname)
        .map(newParent -> new ResponseEntity<>(newParent, HttpStatus.FOUND))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }
  /**

   * .

   */
  @GetMapping("/parents/date/{birthdate}/{birthdate1}")
  public Flux<Parennt> findByBirthdateBetween(@PathVariable("birthdate")
      @DateTimeFormat(iso = ISO.DATE)final Date birthdate,@PathVariable("birthdate1")
      @DateTimeFormat(iso = ISO.DATE)final Date birthdate1) {
    return repos.findByBirthdateBetween(birthdate, birthdate1);
  }
}
