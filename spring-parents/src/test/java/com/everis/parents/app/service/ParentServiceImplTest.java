package com.everis.parents.app.service;

import static org.mockito.Mockito.when;

import com.everis.parents.app.bean.Parennt;
import com.everis.parents.app.repository.ParenRepository;
import com.everis.parents.app.service.impl.ParentServiceImpl;
import java.util.Date;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.reactivestreams.Publisher;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * .
 * @author lriveras
 *
 */
@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class ParentServiceImplTest {

  /**
 * Injec ParenRepository.
 */
  @Mock
  private ParenRepository parentRepository;

  /**
 * Injec ParenServiceImpl. 
 */
  @InjectMocks
  private ParentServiceImpl parentService;

  /**
 * TestService findAll.
 */
  @Test
  public void findAllTest() {
    final Parennt parent = new Parennt();
    parent.setFullname("Walter");
    parent.setGender("Masculino");
    parent.setBirthdate(new Date());
    parent.setTypeID("DNI");
    parent.setNumberID("55556666");
    parent.setIdFamily("14141414");

    when(parentService.findAll()).thenReturn(Flux.just(parent));
    final Flux<Parennt> actua = parentService.findAll();
    assertResults(actua, parent);
  }
  
  /**
 *  TestService findByID. 
 */
  @Test
  public void findById_exist() {
    final Parennt parent2 = new Parennt();
    parent2.setId("102");
    parent2.setFullname("Ramon");
    parent2.setGender("Masculino");
    parent2.setBirthdate(new Date());
    parent2.setTypeID("DNI");
    parent2.setNumberID("10210210");
    parent2.setIdFamily("14141414");
    when(parentRepository.findById(parent2.getId())).thenReturn(Mono.just(parent2));
    final Mono<Parennt> actual = parentRepository.findById(parent2.getId());
    assertResults(actual, parent2);
  }

  /**
 * TestService findByID.  
 */
  @Test
  public void findById_not_exist() {
    final Parennt par = new Parennt();
    par.setId("101");
    par.setFullname("Tito");
    par.setGender("Masculino");
    par.setBirthdate(new Date());
    par.setTypeID("DNI");
    par.setNumberID("10110110");
    par.setIdFamily("14141414");
    when(parentRepository.findById(par.getId())).thenReturn(Mono.empty());
    final Mono<Parennt> actual = parentRepository.findById(par.getId());
    assertResults(actual);
  }
  
  /**
 * TestService dave.  
 */
  @Test
  public void save() {
    final Parennt s = new Parennt();
    s.setId("10");
    s.setFullname("Victor");
    s.setGender("Masculino");
    s.setBirthdate(new Date());
    s.setTypeID("DNI");
    s.setNumberID("44443333");
    s.setCreatedAt(new Date());
    s.setIdFamily("14141414");
    when(parentRepository.save(s)).thenReturn(Mono.just(s));
    final Mono<Parennt> actual = parentService.save(s);
    assertResults(actual, s);
  }
  
  /**
 * TestService delete.  
 */
  @Test
  public void delete() {
    final Parennt par = new Parennt();
    par.setId("10");
    par.setFullname("Victor");
    par.setGender("Masculino");
    par.setBirthdate(new Date());
    par.setTypeID("DNI");
    par.setNumberID("44443333");
    par.setCreatedAt(new Date());
    par.setIdFamily("14141414");
    when(parentRepository.delete(par)).thenReturn(Mono.empty());
  }

  /**
 * TestService findByNumberID.
 */
  @Test
  public void findBynNumberID() {
    final Parennt s = new Parennt();
    s.setId("1234");
    s.setFullname("Pedro");
    s.setGender("Masculino");
    s.setBirthdate(new Date());
    s.setTypeID("DNI");
    s.setNumberID("12312312");
    s.setIdFamily("14141414");
    when(parentRepository.findByNumberID("12312312")).thenReturn(Mono.just(s));
    final Mono<Parennt> actual = parentService.findByNumberID("12312312");
    assertResults(actual,s);
  }

  /**
 * TestService findByName.
 */
  @Test
  public void findByName() {
    final Parennt s = new Parennt();
    s.setId("123");
    s.setFullname("Felix");
    s.setGender("Masculino");
    s.setBirthdate(new Date());
    s.setTypeID("DNI");
    s.setNumberID("736723727");
    s.setIdFamily("14141414");
    when(parentRepository.findByFullname("Felix")).thenReturn(Flux.just(s));
    final Flux<Parennt> actual = parentService.findByFullname("Felix");
    assertResults(actual,s);
  }

  /**
 * assertResults.
 */
  private void assertResults(final Publisher<Parennt> publisher, Parennt... expectedParents) {
    StepVerifier
        .create(publisher)
        .expectNext(expectedParents)
        .verifyComplete();
  }
  
}
