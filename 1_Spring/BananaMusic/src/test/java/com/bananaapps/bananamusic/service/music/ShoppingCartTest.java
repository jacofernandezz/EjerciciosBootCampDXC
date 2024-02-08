package com.bananaapps.bananamusic.service.music;

import com.bananaapps.bananamusic.domain.music.PurchaseOrderLineSong;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertTrue;


class ShoppingCartTest {

    @Autowired
    ShoppingCart cart;

    @Test
    void testBeans() {
        assertThat(cart, notNullValue());
    }


    @Test
    void when_the_cart_is_created_has_0_items() throws Exception {

        int num = cart.getItemCount();
        double bal = cart.getBalance();

        assertThat(num, is(0));
        assertThat(bal, is(0d));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 5, 10, 1000})
    void when_the_cart_is_empty_has_0_items(int numProducts) throws Exception {
        Random rand = new Random();

        for (int i = 0; i < numProducts; i++) {
            cart.addItem(new PurchaseOrderLineSong(1L, null, 2, rand.nextDouble() * 100));
        }

        cart.empty();

        // then
        int num = cart.getItemCount();
        double bal = cart.getBalance();

        assertThat(num, is(0));
        assertThat(bal, is(0d));
    }

    @Test
    void when_a_new_product_is_added_the_quantity_of_items_must_be_increased() {
        // TODO
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 5, 10, 1000})
    void when_a_new_product_is_added_the_new_balance_must_be_the_sum_of_previous_ones_plus_the_cost_of_the_product(int numProducts) throws Exception {
        // given
        Random rand = new Random();
        double inc = 0;

        // when
        for (int i = 0; i < numProducts; i++) {
            double precio = rand.nextDouble() * 100;
            cart.addItem(new PurchaseOrderLineSong(1L, null, 1, precio));

            inc += precio;
        }

        System.out.println("count: " + cart.getItemCount());

        // then
        int num = cart.getItemCount();
        double bal = cart.getBalance();

        assertThat(num, is(numProducts));
        assertThat(bal, is(inc));
    }

    @Test
    void when_an_element_is_deleted_the_number_of_elements_must_be_decreased() {
        // TODO
    }

    @Test
    void when_you_check_out_a_product_that_is_not_in_the_cart_ProductNotFoundException_should_be_thrown() {
        // TODO
    }

    @Test
    void givenCartNotEmpty_whenBuy_thenOK() throws Exception {

        Random rand = new Random();

        for (int i = 0; i < 3; i++) {
            cart.addItem(new PurchaseOrderLineSong(1L, null, 2, rand.nextDouble() * 100));
        }

        cart.buy();

        assertTrue(true);

    }
}