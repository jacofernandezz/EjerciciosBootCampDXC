package com.bananaapps.bananamusic.service.music;

import java.util.Collection;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bananaapps.bananamusic.domain.music.Song;
import com.bananaapps.bananamusic.persistence.music.SongRepository;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CatalogImpl implements Catalog {

    @Autowired
    private SongRepository songRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Song findById(Long id) {
        return songRepository.findOne(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Collection<Song> findByKeyword(String keyword) {
        return songRepository.findByKeyword(keyword);
    }

    @Override
    @Transactional(propagation = Propagation.NEVER)
    public long size() {
        return songRepository.count();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void save(Song song) {
        songRepository.save(song);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveCollection(Collection<Song> songs) {
        for (Song aSong : songs) {
            System.out.println("Attempting to save " + aSong);
            songRepository.save(aSong);
        }
        System.out.println("If you are seeing this, saveBatch ended normally!");

    }


}
