package com.bananaapps.bananamusic.controller;

import com.bananaapps.bananamusic.domain.music.Song;
import com.bananaapps.bananamusic.domain.music.SongCategory;
import com.bananaapps.bananamusic.service.music.Catalog;
import com.bananaapps.bananamusic.util.JsonUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CatalogController.class)
@ActiveProfiles("dev")
public class CatalogControllerTest_WebMvcTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    Catalog catalogService;

    @BeforeEach
    public void setUp() {
        List<Song> songs = Arrays.asList(
                new Song(1L, "New title", "Demo artist", "2023-12-08", new BigDecimal("4.99"), SongCategory.COUNTRY),
                new Song(2L, "New title2", "Demo artist2", "2023-12-08", new BigDecimal("4.99"), SongCategory.COUNTRY),
                new Song(3L, "New title3", "Demo artist3", "2023-12-08", new BigDecimal("4.99"), SongCategory.COUNTRY)
        );
        Mockito.when(catalogService.findById(1L)).thenReturn(songs.stream().filter(s -> s.getId() == 1L)
                .findFirst().get());
        Mockito.when(catalogService.findByKeyword("artist2")).thenReturn(songs.stream()
                .filter(s -> s.getId() == 2L)
                .collect(Collectors.toList()));
        Mockito.when(catalogService.save(Mockito.any(Song.class)))
                .thenAnswer(invocation -> {
                    Song song = invocation.getArgument(0);
                    song.setId(4L);
                    return song;
                });
    }

    @Test
    void givenValidIdWhenGetSongByIdThenOK() throws Exception {
        long id = 1L;
        mvc.perform(get("/catalog/" + id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void givenValidKeywordWhenGetSongsByKeywordsThenOK() throws Exception {
        String keyword = "artist2";

        mvc.perform(get("/catalog?keyword=" + keyword).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[*].title", contains("New title2")));
    }

    @Test
    void givenValidSongWhenCreateSongThenCreated() throws Exception {
        Song song = new Song(1L, "New title", "Demo artist", "2023-12-08", new BigDecimal("4.99"), SongCategory.COUNTRY);
        mvc.perform(post("/catalog").content(JsonUtil.asJsonString(song))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(greaterThan(0))));
    }

}
