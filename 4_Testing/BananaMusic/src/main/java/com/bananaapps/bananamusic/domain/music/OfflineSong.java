package com.bananaapps.bananamusic.domain.music;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "downloadableItem")
@PrimaryKeyJoinColumn(name = "tuneId")
@XmlRootElement
public class OfflineSong extends Song {

    private String url;
    private String fileType;

    public OfflineSong(String num, String title,
                       String artist, LocalDate releaseDate, BigDecimal price) {
        setTitle(title);
        setArtist(artist);
        setReleaseDate(releaseDate);
        setPrice(price);
    }

}
