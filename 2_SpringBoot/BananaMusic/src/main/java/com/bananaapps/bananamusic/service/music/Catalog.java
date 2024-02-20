package com.bananaapps.bananamusic.service.music;

import java.util.Collection;

import com.bananaapps.bananamusic.domain.music.Song;

public interface Catalog {

	   public Song findById(Long id);
	   public Collection<Song> findByKeyword(String keyword);
	   public long size();
	   public Song save(Song song);
	   public void saveCollection(Collection<Song> songs);
	}