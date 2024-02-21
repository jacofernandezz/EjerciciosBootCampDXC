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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CartController.class)
@ActiveProfiles("dev")
public class CartControllerTest_WebMvcTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    ShoppingCart shoppingCartService;

    @BeforeEach
    public void setUp() {
        Mockito.when(shoppingCartService.getBalance()).thenReturn(0.0);
        Mockito.doNothing().when(shoppingCartService).addItem(Mockito.any(PurchaseOrderLineSong.class));
        Mockito.doNothing().when(shoppingCartService).buy();
    }

    @Test
    void givenEmptyCartWhenGetBalanceThenBalance0() throws Exception {
        mvc.perform(get("/cart/balance").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", is(0.0)));
    }

    @Test
    void whenValidItemWhenAddItemThenItemAdded() throws Exception {
        PurchaseOrderLineSong item = new PurchaseOrderLineSong(1L, null, null, 2, 20.0);
        mvc.perform(put("/cart")
                        .content(JsonUtil.asJsonString(item))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isAccepted());
    }

    @Test
    void givenItemOnCartWhenBuyThenAccepted() throws Exception {
        mvc.perform(post("/cart/buy")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isAccepted());
    }

}
