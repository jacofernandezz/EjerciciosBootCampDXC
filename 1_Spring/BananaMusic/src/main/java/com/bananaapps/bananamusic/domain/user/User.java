package com.bananaapps.bananamusic.domain.user;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class User {
    private Integer id;

    private String email;

    private String password;

    private ERole role;

    public User(Integer id) {
        this.id = id;
    }


}