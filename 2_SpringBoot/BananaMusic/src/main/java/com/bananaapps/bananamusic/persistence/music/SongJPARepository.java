package com.bananaapps.bananamusic.persistence.music;

import com.bananaapps.bananamusic.domain.music.Song;
import com.bananaapps.bananamusic.domain.music.SongCategory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

@Repository
public class SongJPARepository implements SongRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Song findOne(Long id) {
        return em.find(Song.class, id);
    }

    @Override
    public Collection<Song> findAll() {
        return em.createQuery("SELECT s FROM Song s", Song.class).getResultList();
    }

    @Override
    public Collection<Song> findByArtistContainingOrTitleContainingAllIgnoreCase(String artist, String title) {
        return em.createQuery("SELECT s FROM Song s WHERE UPPER(s.artist) LIKE UPPER(:artist) OR UPPER(s.title) LIKE UPPER(:title)", Song.class)
                .setParameter("artist", "%" + artist + "%")
                .setParameter("title", "%" + title + "%")
                .getResultList();
    }

    @Override
    public Collection<Song> findBySongCategory(SongCategory category) {
        return em.createQuery("SELECT s FROM Song s WHERE s.category = :category", Song.class)
                .setParameter("category", category)
                .getResultList();
    }

    @Override
    public long count() {
        return (long) em.createQuery("SELECT COUNT(s) FROM Song s").getSingleResult();
    }

    @Override
    public Song save(Song song) {
        if (song.getId() == null) {
            em.persist(song);
            return song;
        } else {
            return em.merge(song);
        }
    }

    @Override
    public void delete(Song song) {
        em.remove(song);
    }
}
