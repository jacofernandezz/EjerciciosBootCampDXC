package com.bananaapps.bananamusic.service.music;


import com.bananaapps.bananamusic.domain.music.SongCategory;
import com.bananaapps.bananamusic.domain.music.Song;
import com.bananaapps.bananamusic.service.music.Catalog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class CatalogTest {

    @Autowired
	Catalog cat;
    
	// One TX, everything works properly
	@Test
	public void testFind_WithRequiredTX_Positive() {
		Song item = cat.findById(1L);
		assertNotNull(item);
		System.out.println("Found item" + item);
	}
	
	// Save a null item - should throw an exception when saving.
	@Test
	public void testSave_withNullItem_negative() {
		String expectedExceptionEndString = "event with null entity";
		try {
			cat.save(null);
			fail("Exception not thrown");
		}
		catch (IllegalArgumentException iae) {
			assertTrue(iae.getMessage().endsWith(expectedExceptionEndString));
		}
	}
	
	// Non-transactional method called - no TX
	@Test
	public void testSize_noTX_positive() {
		System.out.println(cat.size());
	}
	
	// Batch persistence done with one null item that blows up when persisted
	// Exception thrown, complete TX rolled back with original TX settings.
	@Test
	public void testSaveBatch_withNullEntity_negative() {  
		String expectedExceptionEndString = "event with null entity";
		try {
			cat.saveCollection(grabBatch());
			fail("Exception not thrown");
		}
		catch (IllegalArgumentException iae) {
			assertTrue(iae.getMessage().endsWith(expectedExceptionEndString));
		}
	}
	
	@Test
	public void testSave_WithRequiredTX_positive() {	
		Song saveItem = new Song("SaveItemTitle", "SaveArtist", "2013-01-04",new BigDecimal("13.99"), SongCategory.CLASSICAL);
		assertNull(saveItem.getId());
		cat.save(saveItem);
		Long id = saveItem.getId();
		System.out.println("Save Test...id is: " + id);
		assertNotNull(id);
	}
	

	private Collection<Song> grabBatch() {
		Collection<Song> batch = new ArrayList<Song>();
		add(batch, "New One", "New", "2013-01-04", "13.99", SongCategory.RAP);
		add(batch, "Another New One", "Newer", "2013-02-05", "14.99", SongCategory.POP);
		batch.add(null);
		return batch;
	}

	private void add(Collection<Song> batch, String title, String artist, String releaseDate, String price, SongCategory musicCategory) {
		batch.add(new Song(title, artist, releaseDate,new BigDecimal(price), musicCategory));
	}

}
