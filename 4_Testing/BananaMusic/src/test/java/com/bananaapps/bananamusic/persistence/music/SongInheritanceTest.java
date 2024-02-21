package com.bananaapps.bananamusic.persistence.music;

import com.bananaapps.bananamusic.domain.music.OfflineSong;
import com.bananaapps.bananamusic.domain.music.Song;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestEntityManager
@ComponentScan("com.bananaapps.bananamusic.persistence.music")
@ActiveProfiles("dev")
public class SongInheritanceTest {

	@Autowired
	SongRepository repo;

    @Autowired
	private PlatformTransactionManager transactionManager;		
	
	@Test
	@Transactional
	public void testInheritancePositive() {
		DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
		TransactionStatus transaction = transactionManager.getTransaction(definition);

		Song mi = repo.findOne(1L);
		System.out.println(mi);
		
		assertTrue(mi instanceof OfflineSong);

		Collection<Song> allSongs = repo.findAll();
		allSongs.forEach(i -> System.out.println(i));
        transactionManager.commit(transaction);
	}
}