package com.bananaapps.bananamusic.service.music;

import com.bananaapps.bananamusic.domain.music.PurchaseOrder;
import com.bananaapps.bananamusic.domain.music.PurchaseOrderLineSong;
import com.bananaapps.bananamusic.domain.user.User;
import com.bananaapps.bananamusic.exception.SongNotfoundException;
import com.bananaapps.bananamusic.exception.UserNotfoundException;
import com.bananaapps.bananamusic.persistence.UserRepository;
import com.bananaapps.bananamusic.persistence.music.JpaPurchaseOrderRepository;
import com.bananaapps.bananamusic.persistence.music.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

@Service
public class ShoppingCartImpl implements ShoppingCart {

    private ArrayList<PurchaseOrderLineSong> items;

    @Autowired
    private PurchaseOrderRepository orderRepo;

    @Autowired
    private UserRepository userRepo;

    public ShoppingCartImpl() {
        items = new ArrayList<>();
    }

    @Override
    public double getBalance() {
        double balance = 0.00;
        for (Iterator i = items.iterator(); i.hasNext(); ) {
            PurchaseOrderLineSong item = (PurchaseOrderLineSong) i.next();
            balance += item.getQuantity() * item.getUnitPrice();
        }
        return balance;
    }

    @Override
    public void addItem(PurchaseOrderLineSong item) {
        items.add(item);
    }

    @Override
    public void removeItem(Long item) throws SongNotfoundException {

        for (PurchaseOrderLineSong aItem : items) {
            if (aItem.getLineNumber() == item) {
                items.remove(aItem);
                break;
            }
        }
        throw new SongNotfoundException();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void empty() {
        items.clear();
    }

    @Override
    public void buy() {

        try {
            // simulated user. Must exist in ddbb
            User currentUser = userRepo.findByEmailAndPassword("juan@j.com", "jjjj").orElseThrow(() -> {
                throw new UserNotfoundException();
            });

            PurchaseOrder purchase = new PurchaseOrder(null, 1, LocalDate.of(2024, 1, 28), currentUser, items);

            for (PurchaseOrderLineSong item : items) {
                item.setOrder(purchase);
            }

            if(purchase.isValid()) {
                orderRepo.save(purchase);
                empty();
            }
            else throw new RuntimeException("No valid");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
