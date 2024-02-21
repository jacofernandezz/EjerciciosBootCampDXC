package com.bananaapps.bananamusic.controller;

import com.bananaapps.bananamusic.domain.music.PurchaseOrderLineSong;
import com.bananaapps.bananamusic.exception.ProductNotFoundException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("dev")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CartControllerTest {

    @Autowired
    CartController cartController;

    @Test
    @Order(1)
    void givenEmptyCartWhenGetBalanceThenBalance0() {
        ResponseEntity<Double> response = cartController.getBalance();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEqualTo(0.0);
    }

    @Test
    @Order(2)
    void whenValidItemWhenAddItemThenItemAdded() {
        PurchaseOrderLineSong item = new PurchaseOrderLineSong(1L, null, null, 2, 20.0);
        ResponseEntity<String> response = cartController.addItem(item);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
    }

    @Test
    @Order(4)
    void whenItemOnCartWhenRemoveItemThenItemRemoved() {
        PurchaseOrderLineSong item = new PurchaseOrderLineSong(1L, null, null, 2, 20.0);
        cartController.addItem(item);
        ResponseEntity<String> response = cartController.removeItem(1L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(cartController.getItemCount().getBody()).isEqualTo(0);
    }

    @Test
    @Order(5)
    void givenEmptyCartWhenItemCountThenCount0() {
        ResponseEntity<Integer> response = cartController.getItemCount();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEqualTo(0);
    }

    @Test
    @Order(3)
    void givenItemOnCartWhenEmptyThenCount0() {
        PurchaseOrderLineSong item = new PurchaseOrderLineSong(1L, null, null, 2, 20.0);
        cartController.addItem(item);
        ResponseEntity<String> response = cartController.empty();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(response.getBody()).isNotNull();
        assertThat(cartController.getItemCount().getBody()).isEqualTo(0);
    }

    @Test
    @Order(6)
    void givenNoItemOnCartWhenBuyThenException() {
        assertThrows(ProductNotFoundException.class, () -> cartController.buy());
    }

}
