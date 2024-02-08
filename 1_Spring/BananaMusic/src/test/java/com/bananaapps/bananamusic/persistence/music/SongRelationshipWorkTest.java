package com.bananaapps.bananamusic.persistence.music;

import com.bananaapps.bananamusic.domain.music.Backlog;
import com.bananaapps.bananamusic.domain.music.Song;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Collection;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class SongRelationshipWorkTest {

	@Autowired
    SongRepository repo;

    @Autowired
	private PlatformTransactionManager transactionManager;		
	

	// ****  Test to be run without cascading set
	@Test
	public void testInventoryAddNoCascadeNegative() {
		DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
		TransactionStatus transaction = transactionManager.getTransaction(definition);
		
		Song song = repo.findOne(5L);
		int oldSize = song.getBacklogRecords().size();
		System.out.format("Old size is %d\n", oldSize);
		
		song.addBacklogRecord("Los Angeles", 50);
		
        transactionManager.commit(transaction);
        transaction = transactionManager.getTransaction(definition);
        
        song = repo.findOne(5L);
        int newSize = song.getBacklogRecords().size();
        System.out.format("New size is %d\n", newSize);
		assertNotEquals(oldSize, newSize, "Counts should not be equal");
		
        transactionManager.commit(transaction);
	}

	// ****  Test to be run without cascading set	
	// @Test
	public void testInventoryUpdateNoCascadePositive() {
		DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
		TransactionStatus transaction = transactionManager.getTransaction(definition);
		
		Song mi = repo.findOne(1L);
		
		int oldCount = 0;
		boolean first = true;
		
		Collection<Backlog> backlogRecords = mi.getBacklogRecords();
		
		System.out.println("\n** Backlog records before updating count **");
		for (Backlog cur: backlogRecords) {
			if (first) {
				oldCount = cur.getQuantity();
				first = false;
			}
			System.out.println(cur);
			cur.setQuantity(cur.getQuantity()+100);
			
		}
		System.out.println();
        transactionManager.commit(transaction);
        transaction = transactionManager.getTransaction(definition);
        
        first = true;
		mi = repo.findOne(1L);
		backlogRecords = mi.getBacklogRecords();
		System.out.println("\n** Backlog records AFTER updating count **");
		for (Backlog cur: backlogRecords) {
			if (first) {
				assertNotEquals( oldCount, cur.getQuantity(), "Counts should not be equal");
				first = false;
			}
			System.out.println(cur);
		}
		System.out.println();
		transactionManager.commit(transaction);
	}	

	// **** Test to be run WITHOUT orphan removal
	// @Test
	public void testRemoveNoOrphanRemovalNegative() {
		DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
		TransactionStatus transaction = transactionManager.getTransaction(definition);
		
		Song song = repo.findOne(1L);

		Collection<Backlog> backlogRecords = song.getBacklogRecords();
		int oldSize = backlogRecords.size();
		
		System.out.println("\nBacklog before removal");
		backlogRecords.forEach(cur->System.out.println(cur));
		System.out.println();
		Iterator<Backlog> i = backlogRecords.iterator();
		Backlog anInventory = i.next();  // Get the first inventory record.
		
		backlogRecords.remove(anInventory);

		transactionManager.commit(transaction);
		transaction = transactionManager.getTransaction(definition);
		
		song = repo.findOne(1L);
		
		backlogRecords = song.getBacklogRecords();
		int newSize = backlogRecords.size();
		
		System.out.println("\nBacklog after removal");
		backlogRecords.forEach(cur->System.out.println(cur));
		System.out.println();
		
		// This test should FAIL here
		assertNotEquals( oldSize, newSize, "Backlog sizes should not be equal after removal");
		
		transactionManager.commit(transaction);
	}	

	// **** Test to be run WITH orphan removal
	// @Test
	public void testRemoveWithOrphanRemovalPositive() {
		DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
		TransactionStatus transaction = transactionManager.getTransaction(definition);
		
		Song song = repo.findOne(1L);

		// DONE: Get the inventory records and their size
		Collection<Backlog> backlogRecords = song.getBacklogRecords();
		int oldSize = backlogRecords.size();
		
		System.out.println("\nBacklog before removal");
		backlogRecords.forEach(cur->System.out.println(cur));
		System.out.println();
		Iterator<Backlog> i = backlogRecords.iterator();
		Backlog anInventory = i.next();

		backlogRecords.remove(anInventory);

		transactionManager.commit(transaction);
		transaction = transactionManager.getTransaction(definition);
		
		song = repo.findOne(1L);
		
		backlogRecords = song.getBacklogRecords();
		int newSize = backlogRecords.size();
		
		System.out.println("\nBacklog after removal");
		backlogRecords.forEach(cur->System.out.println(cur));
		System.out.println();
		
		// This test should SUCCEED now
		assertNotEquals( oldSize, newSize, "Backlog sizes should not be equal after removal");
		
		transactionManager.commit(transaction);
	}	

	
	@Test
	public void testLazyFetchIllustrateOnly() {
		DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
		TransactionStatus transaction = transactionManager.getTransaction(definition);
		
		Song mi = repo.findOne(1L);
		Collection<Backlog> backlogRecords = mi.getBacklogRecords();
		System.out.println("\nAbout to display inventory records");
		backlogRecords.forEach(cur->System.out.println(cur));
		System.out.println();
		transactionManager.commit(transaction);
	}	

	
	@Test
	// @Test(expected = LazyInitializationException.class)
	public void testLazyFetchNegative() {
		DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
		TransactionStatus transaction = transactionManager.getTransaction(definition);
		
		Song song = repo.findOne(1L);
		transactionManager.commit(transaction);
		
		Collection<Backlog> inventoryRecords = song.getBacklogRecords();
		System.out.println("\nAbout to display inventory records");
		inventoryRecords.forEach(cur->System.out.println(cur));
		System.out.println();
	}

}
