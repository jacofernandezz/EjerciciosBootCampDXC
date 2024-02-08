package com.bananaapps.bananamusic.domain.music;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Backlog {
    private Long id;
    private String location;
    private int quantity;
    private int version;
    private Song item;

    public Backlog(String location, int quantity) {
        // Set the location and quantity
        setLocation(location);
        setQuantity(quantity);
    }

}
