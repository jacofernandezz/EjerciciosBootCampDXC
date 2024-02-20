package com.bananaapps.bananamusic.persistence.music;

import com.bananaapps.bananamusic.domain.music.Song;
import com.bananaapps.bananamusic.domain.music.SongCategory;

import java.util.Collection;

public interface SongRepository {

    public Song findOne(Long id);

    public Collection<Song> findAll();

    public default Collection<Song> findByKeyword(String keyword) {
        return findByArtistContainingOrTitleContainingAllIgnoreCase(keyword, keyword);
    }

    public Collection<Song> findByArtistContainingOrTitleContainingAllIgnoreCase(String artist, String title);

    public Collection<Song> findBySongCategory(SongCategory category);

    public long count();

    public Song save(Song song);

    public void delete(Song song);
}
