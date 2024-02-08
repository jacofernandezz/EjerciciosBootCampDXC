package com.microcompany.oauth2apiclient.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Song {
    private Long id;
    private String title, artist;
    private LocalDate releaseDate;
    private BigDecimal price;
    private SongCategory songCategory;
    private int version;
}
