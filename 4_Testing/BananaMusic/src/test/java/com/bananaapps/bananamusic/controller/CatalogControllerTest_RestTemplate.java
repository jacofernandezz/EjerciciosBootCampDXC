package com.bananaapps.bananamusic.controller;

import com.bananaapps.bananamusic.domain.music.Song;
import com.bananaapps.bananamusic.domain.music.SongCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
public class CatalogControllerTest_RestTemplate {

    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    void givenValidKeywordWhenGetSongsByKeywordsThenOK() {
        String keyword = "Creed";

        ResponseEntity<String> response = testRestTemplate
                .getForEntity("http://localhost:" + port + "/catalog?keyword=" + keyword,
                        String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("Human Clay");
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
    }

    @Test
    void givenValidSongWhenCreateSongThenCreated() {
        Song song = new Song( "New title", "Demo artist", "2023-12-08", new BigDecimal("4.99"), SongCategory.COUNTRY);
        HttpHeaders headers = new HttpHeaders();
        headers.set("ACCEPT", "application/json");
        HttpEntity<Song> request = new HttpEntity<>(song, headers);
        ResponseEntity<String> response = testRestTemplate
                .postForEntity("http://localhost:" + port + "/catalog", request,
                        String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    }

}
