package com.bananaapps.bananamusic.config;

import com.bananaapps.bananamusic.persistence.music.InMemorySongRepository;
import com.bananaapps.bananamusic.persistence.music.SongRepository;
import com.bananaapps.bananamusic.service.music.Catalog;
import com.bananaapps.bananamusic.service.music.CatalogImpl;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = {"com.bananaapps.bananamusic.persistence", "com.bananaapps.bananamusic.service"})
@Import({ProdConfig.class})
@EntityScan("com.bananaapps.bananamusic.domain")
@EnableJpaRepositories("com.bananaapps.bananamusic.persistence")
@EnableAutoConfiguration
public class SpringConfig {

    @Bean
    @Profile("dev")
    public SongRepository getSongRepository() { return new InMemorySongRepository(); }

    @Bean
    public Catalog getCatalog() { return new CatalogImpl(); }

}
