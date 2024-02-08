package com.bananaapps.bananamusic.service.music;

import com.bananaapps.bananamusic.domain.music.PurchaseOrderLineSong;
import com.bananaapps.bananamusic.exception.SongNotfoundException;

public interface ShoppingCart {
    double getBalance();

    void addItem(PurchaseOrderLineSong item);

    void removeItem(Long item) throws SongNotfoundException;

    int getItemCount();

    void empty();

    void buy();
}