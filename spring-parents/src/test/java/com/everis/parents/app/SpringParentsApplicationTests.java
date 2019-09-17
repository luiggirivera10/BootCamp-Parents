package com.everis.parents.app;

import com.everis.parents.app.bean.Parennt;
import com.everis.parents.app.repository.ParenRepository;
import java.util.Date;
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
  public void crear() {
    Parennt parennt = new Parennt("8", "Rodrigo", "Masculino", new Date(), "DNI", "33331010",
        new Date(), "Luiggi");
    client.post().uri("api/v1.0/parents").contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8).body(Mono.just(parennt), Parennt.class).exchange()
        .expectStatus().isCreated().expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
        .expectBody(Parennt.class).consumeWith(response -> {
          Parennt ps = response.getResponseBody();
          Assertions.assertThat(ps.getId()).isNotEmpty();
          Assertions.assertThat(ps.getFullname()).isEqualTo("Rodrigo");

        });
  }

}
