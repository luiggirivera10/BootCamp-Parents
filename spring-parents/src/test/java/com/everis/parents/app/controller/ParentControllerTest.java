package com.everis.parents.app.controller;

import com.everis.parents.app.bean.Parennt;
import com.everis.parents.app.repository.ParenRepository;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * .
 * @author lriveras
 *
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ParentControllerTest {

  /**
   * Injected AppContext.
   */
  @Autowired
  private ApplicationContext applicationContext;

  /**
   * .
   */
  @Autowired
  private ParenRepository parenntRepository;
  private WebTestClient client;
  private List<Parennt> expectedParennts;

  /**
   * .
   */
  @BeforeEach
  void setUp() {
    client = WebTestClient
      .bindToApplicationContext(applicationContext)
      .configureClient()
      .baseUrl("/api/v1.0")
      .build();

    Flux<Parennt> initData = parenntRepository.deleteAll()
        .thenMany(Flux.just(
        Parennt.builder().id("1").fullname("Andres").gender("Masculino").birthdate(new Date())
        .typeID("DNI").numberID("74306050").idFamily("101").build(),
        Parennt.builder().id("2").fullname("Rodrigo").gender("Masculino").birthdate(new Date())
        .typeID("DNI").numberID("74306051").idFamily("102").build())
        .flatMap(parenntRepository::save))
        .thenMany(parenntRepository.findAll());

    expectedParennts = initData.collectList().block();
  }

  /**
   * Test FindAll.
   */
  @Test
  void findAll() {

    client.get().uri("/parents").exchange()
      .expectStatus().isOk();
  }

  /**
   * Test Save.
   */
  @Test
  void save() {
    Parennt expectedTeach = expectedParennts.get(0);
    client.post().uri("/parents").body(Mono.just(expectedTeach), Parennt.class).exchange()
      .expectStatus().isCreated();
  }

  /**
   * Test findByID.
   */
  @Test
  void findById() {

    String id = "1";
    client.get().uri("/parents/{id}", id).exchange()
      .expectStatus().isOk();
  }

  /**
   * Test Update.
   */
  @Test
  void update() {

    Parennt expectedTeach = expectedParennts.get(0);

    client.put().uri("/parents/{id}", expectedTeach.getId())
    .body(Mono.just(expectedTeach), Parennt.class).exchange()
      .expectStatus().isCreated();
  }

  /**
   * Test Delete.
   */
  @Test
  void delete() {

    Parennt teachDelete = expectedParennts.get(0);
    client.delete().uri("/parents/{id}", teachDelete.getId()).exchange()
      .expectStatus().isOk();
  }

  /**
   * FindByNumberID.
   */
  @Test
  void findByNumberID() {

    String numberID = "74306051";
    client.get().uri("/parents/doc/{numberID}", numberID).exchange()
      .expectStatus().isFound();
  }

  /**
   * Test FindByName.
   */
  @Test
  void findByName() {

    String fullname = "Rodrigo";
    client.get().uri("/parents/nombre/{fullname}", fullname).exchange()
      .expectStatus().isFound();
  }

  /**
   * Test FindByBirthDateBetween.
   */
  @Test
  void findBybirthdateBetween() {

    LocalDate birthdate = LocalDate.of(2018,03,02);
    LocalDate birthdate1 = LocalDate.of(2019,03,02);
    client.get().uri("/parents/date/{birthdate}/{birthdate1}", birthdate,birthdate1).exchange()
        .expectStatus().isOk()
        .expectBodyList(Parennt.class);

  }

  /**
   * Test FindByName.
   */
  @Test
  void findByIdFamily() {

    String idFamily = "102";
    client.get().uri("/parents/family/{idFamily}", idFamily).exchange()
      .expectStatus().isOk();
  }
}
