package com.bananaapps.bananamusic.domain.music;

import com.bananaapps.bananamusic.domain.user.User;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PurchaseOrder {
    private Long id;
    private int status;
    private boolean valid;
    private LocalDate orderDate;
    private User user;
    private List<PurchaseOrderLineSong> lineSongs;


    public boolean isValid() {
        return (orderDate.isBefore(LocalDate.now()) && user != null && user.getId() > 0L && lineSongs.size() > 0);
    }

    public double getTotalPrice() {
        double result = 0;
        if (lineSongs != null) {
            for (PurchaseOrderLineSong line : lineSongs) {
                result += line.getQuantity() * line.getUnitPrice();
            }
        }
        return result;
    }

    public PurchaseOrder(Long id, int status, LocalDate orderDate, User user, List<PurchaseOrderLineSong> lineSongs) {
        this.id = id;
        this.status = status;
        this.orderDate = orderDate;
        this.user = user;
        this.lineSongs = lineSongs;
    }
}
