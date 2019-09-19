package com.everis.parents.app.controller;

import com.everis.parents.app.bean.Parennt;
import com.everis.parents.app.repository.ParenRepository;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

/**
 *. 
 * @author lriveras
 *
 */
@AutoConfigureWebTestClient
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ParentRestControllerTest {

  /**
 * Injection WebTestClient.
 */
  @Autowired
  private WebTestClient client;
  /**
 * Injection ParenRepository.
 */
  @Autowired
  private ParenRepository service;

  /**
 * Test findAll.
 */
  @Test
  public void findAll() {
    client.get().uri("/api/v1.0/parents")
         .accept(MediaType.APPLICATION_JSON_UTF8)
         .exchange().expectStatus().isOk()
         .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
         .expectBodyList(Parennt.class)
         .consumeWith(response -> {
           final List<Parennt> parents = response.getResponseBody();
           parents.forEach(s -> {
             System.out.println(s.getFullname() + " - " + s.getNumberID());
           });
           Assertions.assertThat(parents.size() > 0).isTrue();
         });
  }

  /**
 * Test findByID.
 */
  @Test
public void findById() {
    final Parennt parent = service.findByFullname_par("David").block();
    if (parent != null) {
      client.get().uri("/api/v1.0/parents/{id}", Collections.singletonMap("id", parent.getId()))
          .accept(MediaType.APPLICATION_JSON_UTF8)
          .exchange()
          .expectStatus().isOk()
          .expectHeader()
          .contentType(MediaType.APPLICATION_JSON_UTF8)
          .expectBody(Parennt.class).consumeWith(response -> {
            final Parennt pr = response.getResponseBody();
            Assertions.assertThat(pr.getId()).isNotEmpty();
            Assertions.assertThat(pr.getId().length() > 0).isTrue();
            Assertions.assertThat(pr.getFullname()).isEqualTo("David");
          });
    }
  }

  /**
 * Test save.
 */
  @Test
  public void save() {
    final Parennt parennt = new Parennt("Rominax", "Femenino", new Date(), "DNI", "00000001", "23");
    client.post().uri("/api/v1.0/parents").contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8).body(Mono.just(parennt), Parennt.class).exchange()
        .expectStatus().isOk().expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
        .expectBody(Parennt.class).consumeWith(response -> {
          final Parennt paren = response.getResponseBody();
          Assertions.assertThat(paren.getId()).isNotEmpty();
          Assertions.assertThat(paren.getFullname()).isEqualTo("Rominax");
        });
  }

  /**
 * Test update.
 */
  @Test
  public void updateTest() {
    final Parennt parent = service.findByFullname_par("Sandrox").block();
    final Parennt parentEditado = new Parennt("Sandro", "Masculino",
        new Date(),"DNI", "20020711","22");
    if (parent != null) {
      client.put().uri("/api/v1.0/parents/{id}",Collections.singletonMap("id", parent.getId()))
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8)
     .body(Mono.just(parentEditado), Parennt.class)
     .exchange()
     .expectStatus().isOk()
     .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
     .expectBody()
     .jsonPath("$.id").isNotEmpty()
     .jsonPath("$.fullname").isEqualTo("Sandro")
     .jsonPath("$.numberID").isEqualTo("20020711");
    }
  }

  /**
 * Test findByFullname.
 */
  @Test
  public void findByFullnameTest() {
    final Parennt parent = service.findByFullname_par("David").block();
    if (parent != null) {
      client.get()
        .uri("/api/v1.0/parents/nombre/{fullname}",
            Collections.singletonMap("fullname", parent.getFullname()))
        .exchange()
        .expectStatus().isOk()
        .expectBody().jsonPath("$.fullname").isEqualTo("David");
    }
  }

  /**
 * test findByNumberID.
 */
  @Test
  public void findByNumberIdTest() {
    final Parennt parent = service.findByFullname_par("Lucia").block();
    if (parent != null) {
      client.get()
        .uri("/api/v1.0/parents/doc/{numberID}",
        Collections.singletonMap("numberID", parent.getNumberID()))
        .exchange()
        .expectStatus().isOk()
        .expectBody().jsonPath("$.numberID").isEqualTo("20100711"); 
    }
  }

  /**
 * Test  delete.
 */
  @Test
  public void deleteTest() {
    Parennt parent = service.findByNumberID("00000000").block();
    if (parent != null) {
      client.delete()
      .uri("/api/v1.0/parents/{id}",Collections.singletonMap("id", parent.getId()))
      .exchange()
      .expectStatus().isOk()
      .expectBody()
        .isEmpty();
    }

  }
}
