package com.everis.parents.app;

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

@AutoConfigureWebTestClient
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringParentsApplicationTests {

  @Autowired
  private WebTestClient client;

  @Autowired
  private ParenRepository service;

  @Test
  public void findAll() {
    client.get().uri("/api/v1.0/parents")
         .accept(MediaType.APPLICATION_JSON_UTF8)
         .exchange().expectStatus().isOk()
         .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
         .expectBodyList(Parennt.class)
         .consumeWith(response -> {
           List<Parennt> parents = response.getResponseBody();
           parents.forEach(s -> {
             System.out.println(s.getFullname() + " - " + s.getNumberID());
           });
           Assertions.assertThat(parents.size() > 0).isTrue();
         });
  }

  @Test
public void show() {
    Parennt parent = service.obtenerPorName("David").block();

    client.get().uri("/api/v1.0/parents/{id}", Collections.singletonMap("id", parent.getId()))
        .accept(MediaType.APPLICATION_JSON_UTF8)
        .exchange()
        .expectStatus().isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .expectBody(Parennt.class).consumeWith(response -> {
          Parennt pr = response.getResponseBody();
          Assertions.assertThat(pr.getId()).isNotEmpty();
          Assertions.assertThat(pr.getId().length() > 0).isTrue();
          Assertions.assertThat(pr.getFullname()).isEqualTo("David");
        });
  }

  @Test
  public void crear() {
    Parennt parennt = new Parennt("Romina", "Femenino", new Date(), "DNI", "00000000", "23");
    client.post().uri("/api/v1.0/parents").contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8).body(Mono.just(parennt), Parennt.class).exchange()
        .expectStatus().isOk().expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
        .expectBody(Parennt.class).consumeWith(response -> {
          Parennt ps = response.getResponseBody();
          Assertions.assertThat(ps.getId()).isNotEmpty();
          Assertions.assertThat(ps.getFullname()).isEqualTo("Romina");
        });
  }


  @Test
  public void editarTest() {
    Parennt parent = service.obtenerPorName("Sandrox").block();
    Parennt parentEditado = new Parennt("Sandro", "Masculino", new Date(),"DNI", "20020711","22");

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

  @Test
  public void buscarNombre() {
    Parennt parent = service.obtenerPorName("David").block();
    client.get()
        .uri("/api/v1.0/students/nombre/{fullname}",
            Collections.singletonMap("fullname", parent.getFullname()))
        .exchange()
        .expectStatus().isOk()
        .expectBody().jsonPath("$.fullname").isEqualTo("David");
  }
  
  @Test
  public void buscarDni() {
    Parennt parent = service.obtenerPorName("Lucia").block();
    client.get()
        .uri("/api/v1.0/parents/doc/{numberID}",
        Collections.singletonMap("numberID", parent.getNumberID()))
        .exchange()
        .expectStatus().isOk()
        .expectBody().jsonPath("$.numberID").isEqualTo("20100711");
  }
  
  
  @Test
  public void eliminarTest() {
    Parennt parent = service.findByNumberID("00000001").block();
    client.delete()
        .uri("/api/v1.0/parents/{id}",Collections.singletonMap("id", parent.getId()))
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .isEmpty();
  }
}
