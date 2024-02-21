package com.bananaapps.bananamusic.controller;

import com.bananaapps.bananamusic.domain.music.Song;
import com.bananaapps.bananamusic.domain.music.SongCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("dev")
public class CatalogControllerTest {

    @Autowired
    private CatalogController catalogController;

    @Test
    void givenValidIdWhenGetSongByIdThenOK () {
        ResponseEntity<Song> response = catalogController.getSongById(1L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void givenValidKeywordWhenGetSongsByKeywordsThenOK () {
        ResponseEntity<Collection<Song>> response = catalogController.getSongsByKeywords("Creed");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void givenValidSongWhenCreateSongThenCreated () {
        Song song = new Song("New title", "Demo artist", "2023-12-08", new BigDecimal("4.99"), SongCategory.COUNTRY);
        ResponseEntity<Song> response = catalogController.createSong(song);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(Objects.requireNonNull(response.getBody()).getId()).isGreaterThan(0);
    }

    @Test
    void givenValidSongsWhenCreateSongsThenAccepted () {
        Song song = new Song("New title", "Demo artist", "2023-12-08", new BigDecimal("4.99"), SongCategory.COUNTRY);
        Song song2 = new Song("New title 2", "Demo artist 2", "2023-12-08", new BigDecimal("4.99"), SongCategory.COUNTRY);
        Collection<Song> songs = List.of(song, song2);
        ResponseEntity<Void> response = catalogController.createSongs(songs);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
    }
}
