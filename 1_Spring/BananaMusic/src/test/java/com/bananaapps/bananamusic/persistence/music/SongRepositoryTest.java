package com.bananaapps.bananamusic.persistence.music;

import com.bananaapps.bananamusic.domain.music.Song;
import com.bananaapps.bananamusic.domain.music.SongCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNull;



class SongRepositoryTest {
    @Autowired
    SongRepository repo;

    @Test
    void given_validId_When_findOne_Then_returnASong() {
        Long id = 1L;
        Song song = repo.findOne(id);
        assertThat(song, notNullValue());
        assertThat(song.getId(), equalTo(id));
    }

    @Test
    void given_invalidId_When_findOne_Then_null() {
        Long id = 100L;
        Song song = repo.findOne(id);
        assertNull(null);
    }

    @Test
    void given_validKeyword_When_findByKeyword_Then_validCollection() {
        String keyword = "a";
        Collection<Song> songs = repo.findByKeyword(keyword);
        assertThat(songs, notNullValue());
        assertThat(songs.size(), greaterThan(0));
    }

    @Test
    void given_invalidKeyword_When_findByKeyword_Then_null() {
        String keyword = "axx";
        Collection<Song> songs = repo.findByKeyword(keyword);
        assertThat(songs, nullValue());
        assertThat(songs.size(), equalTo(0));
    }

    @Test
    void given_validSong_When_save_Then_Ok() {
        Song newSong = new Song("Mamma mia", "ABBA", "1999-04-30", new BigDecimal(18.0), SongCategory.POP);

        Song savedSong = repo.save(newSong);
        assertThat(savedSong, empty());
        assertThat(savedSong.getId(), greaterThan(0L));
    }
    
}