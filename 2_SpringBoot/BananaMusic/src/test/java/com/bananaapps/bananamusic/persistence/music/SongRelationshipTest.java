package com.bananaapps.bananamusic.persistence.music;

import com.bananaapps.bananamusic.domain.music.Backlog;
import com.bananaapps.bananamusic.domain.music.Song;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SongRelationshipTest {

    @Autowired
    SongRepository repo;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Test
    public void testBacklogAccessPositive() {
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        TransactionStatus transaction = transactionManager.getTransaction(definition);

        Song song = repo.findOne(1L);

        Collection<Backlog> backlogRecords = song.getBacklogRecords();
        assertNotNull(backlogRecords, "backlogRecords should not be null");
        assertFalse(backlogRecords.isEmpty(), "This song should have inventory records");

        System.out.println("\n***Backlog records for song with id == 1:***");
        for (Backlog cur : backlogRecords) {
            System.out.println(cur);
        }
        System.out.println("***    ***\n");

        transactionManager.commit(transaction);
    }

    //Este test, en un contexto donde los datos se persisten, solo devolverá el resultado esperado la primera vez que se ejecuta,
    //así que se anota con Transactional para que realice un rollback desde de su ejecución
    @Test
    @Transactional
    public void testBacklogAddPositive() {
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        TransactionStatus transaction = transactionManager.getTransaction(definition);

        Song song = repo.findOne(4L);
        assertTrue(song.getBacklogRecords().isEmpty(), "This song should not have inventory records");

        song.addBacklogRecord("Austin", 22);

        transactionManager.commit(transaction);
        transaction = transactionManager.getTransaction(definition);

        song = repo.findOne(4L);
        Collection<Backlog> inventoryRecords = song.getBacklogRecords();
        assertTrue(inventoryRecords.size() == 1, "Should be one inventory record");

        transactionManager.commit(transaction);
    }

}
