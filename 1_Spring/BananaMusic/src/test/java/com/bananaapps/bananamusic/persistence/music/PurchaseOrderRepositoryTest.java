package com.bananaapps.bananamusic.persistence.music;

import com.bananaapps.bananamusic.domain.music.PurchaseOrder;
import com.bananaapps.bananamusic.domain.music.PurchaseOrderLineSong;
import com.bananaapps.bananamusic.domain.music.Song;
import com.bananaapps.bananamusic.domain.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


class PurchaseOrderRepositoryTest {

    @Autowired
    PurchaseOrderRepository repo;

    @Test
    void given_validId_When_getById_Then_Order() {
        Long id = 1L;
        PurchaseOrder order = repo.getById(id);
        assertThat(order, notNullValue());
        assertThat(order.getId(), equalTo(id));
    }

    @Test
    void given_orders_WHEN_findAll_Then_list() {
        Collection orders = repo.findAll();
        assertThat(orders, notNullValue());
        assertThat(orders.size(), greaterThan(0));
    }

    @Test
    void given_existingOrder_WHEN_save_Then_OK() {

        List<PurchaseOrderLineSong> lines = List.of(
                new PurchaseOrderLineSong(null, new Song(1l), 1, 10.0)
        );

        PurchaseOrder order = new PurchaseOrder(null, 1, true, LocalDate.now(), new User(1), lines);

        // TODO: uncomment when relations set
        /*for (PurchaseOrderLineSong line : lines) {
            line.setOrder(order);
        }*/

        order = repo.save(order);

        assertThat(order, notNullValue());
        assertThat(order.getId(), greaterThan(0L));

    }

    @Test
    void given_existingOrder_WHEN_delete_Then_OK() {
    }
}