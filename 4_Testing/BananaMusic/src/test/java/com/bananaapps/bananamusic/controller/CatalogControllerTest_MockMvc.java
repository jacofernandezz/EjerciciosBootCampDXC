package com.bananaapps.bananamusic.controller;

import com.bananaapps.bananamusic.domain.music.Song;
import com.bananaapps.bananamusic.domain.music.SongCategory;
import com.bananaapps.bananamusic.util.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class CatalogControllerTest_MockMvc {

    @Autowired
    MockMvc mvc;

    @Test
    void givenValidIdWhenGetSongByIdThenOK () throws Exception {
        long id = 1L;
        mvc.perform(get("/catalog/" + id).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void givenValidKeywordWhenGetSongsByKeywordsThenOK () throws Exception {
        String keyword = "Creed";

        mvc.perform(get("/catalog?keyword=" + keyword).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[*].title", contains("Human Clay")));
    }

    @Test
    void givenValidSongWhenCreateSongThenCreated () throws Exception {
        Song song = new Song("New title", "Demo artist", "2023-12-08", new BigDecimal("4.99"), SongCategory.COUNTRY);
        mvc.perform(post("/catalog").content(JsonUtil.asJsonString(song))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(greaterThan(0))));
    }

    @Test
    void givenValidSongsWhenCreateSongsThenAccepted () throws Exception {
        Song song = new Song("New title", "Demo artist", "2023-12-08", new BigDecimal("4.99"), SongCategory.COUNTRY);
        Song song2 = new Song("New title 2", "Demo artist 2", "2023-12-08", new BigDecimal("4.99"), SongCategory.COUNTRY);
        Collection<Song> songs = List.of(song, song2);
        mvc.perform(put("/catalog").content(JsonUtil.asJsonString(songs))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }
}
