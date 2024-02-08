package com.bananaapps.bananamusic.domain.music;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
@ToString
public class PurchaseOrderLineSong {
    private Long lineNumber;
    private Song song;
    private Integer quantity;

    public PurchaseOrderLineSong(Long lineNumber, Song song, Integer quantity, Double unitPrice) {
        this.lineNumber = lineNumber;
        this.song = song;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    private Double unitPrice;


}
