package com.microcompany.oauth2apiclient.controller;

import com.microcompany.oauth2apiclient.model.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

@RestController
@RequestMapping("/catalog")
public class CatalagoController {
    @Autowired
    private WebClient webClient;

    @GetMapping(value = "/keyword-view/{keyword}")
    public List<Song> getSongsByKeywords(
            @RegisteredOAuth2AuthorizedClient("music-client-authorization-code") OAuth2AuthorizedClient authorizedClient,
            @PathVariable String keyword
    ) {
        return this.webClient
                .get()
                .uri("http://localhost:9090/catalog/keyword/" + keyword)
                .attributes(oauth2AuthorizedClient(authorizedClient))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Song>>() {
                })
                .block();

    }
}