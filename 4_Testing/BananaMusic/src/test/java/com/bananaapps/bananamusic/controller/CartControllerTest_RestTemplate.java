package com.bananaapps.bananamusic.controller;

import com.bananaapps.bananamusic.domain.music.PurchaseOrderLineSong;
import com.bananaapps.bananamusic.service.music.ShoppingCart;
import com.bananaapps.bananamusic.util.JsonUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
public class CartControllerTest_RestTemplate {

    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate testRestTemplate;

    @MockBean
    ShoppingCart shoppingCartService;

    @BeforeEach
    public void setUp() {
        Mockito.when(shoppingCartService.getBalance()).thenReturn(0.0);
        Mockito.doNothing().when(shoppingCartService).addItem(Mockito.any(PurchaseOrderLineSong.class));
        Mockito.doNothing().when(shoppingCartService).buy();
    }

    @Test
    void givenEmptyCartWhenGetBalanceThenBalance0() {
        ResponseEntity<Double> response = testRestTemplate
                .getForEntity("http://localhost:" + port + "/cart/balance", Double.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(0.0);
    }

    @Test
    void whenValidItemWhenAddItemThenItemAdded() {
        PurchaseOrderLineSong item = new PurchaseOrderLineSong(1L, null, null, 2, 20.0);
        HttpHeaders headers = new HttpHeaders();
        headers.set("ACCEPT", "application/json");
        HttpEntity<PurchaseOrderLineSong> request = new HttpEntity<>(item, headers);
        ResponseEntity<String> response = testRestTemplate.exchange("http://localhost:" + port + "/cart", HttpMethod.PUT, request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
    }

    @Test
    void givenItemOnCartWhenBuyThenAccepted() {
        ResponseEntity<String> response = testRestTemplate.exchange("http://localhost:" + port + "/cart/buy", HttpMethod.POST, HttpEntity.EMPTY, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
    }

}
