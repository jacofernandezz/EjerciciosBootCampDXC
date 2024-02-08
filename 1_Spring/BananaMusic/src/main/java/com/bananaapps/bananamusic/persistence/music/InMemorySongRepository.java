package com.bananaapps.bananamusic.persistence.music;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import com.bananaapps.bananamusic.domain.music.SongCategory;
import com.bananaapps.bananamusic.domain.music.Song;

public class InMemorySongRepository implements SongRepository {

    private List<Song> catalogData = new ArrayList<>(Arrays.asList(
            new Song(1L, "Diva", "Annie Lennox", "1992-01-04", new BigDecimal("13.99"), SongCategory.POP),
            new Song(2L, "Dream of the Blue Turtles", "Sting", "1985-02-05", new BigDecimal("14.99"), SongCategory.POP),
            new Song(3L, "Trouble is...", "Kenny Wayne Shepherd Band", "1997-08-08", new BigDecimal("14.99"), SongCategory.BLUES),
            new Song(4L, "Lie to Me", "Jonny Lang", "1997-08-26", new BigDecimal("17.97"), SongCategory.BLUES),
            new Song(5L, "Little Earthquakes", "Tori Amos", "1992-01-18", new BigDecimal("14.99"), SongCategory.ALTERNATIVE),
            new Song(6L, "Seal", "Seal", "1991-08-18", new BigDecimal("17.97"), SongCategory.POP),
            new Song(7L, "Ian Moore", "Ian Moore", "1993-12-05", new BigDecimal("9.97"), SongCategory.CLASSICAL),
            new Song(8L, "So Much for the Afterglow", "Everclear", "1997-01-19", new BigDecimal("13.99"), SongCategory.ROCK),
            new Song(9L, "Surfacing", "Sarah McLachlan", "1997-12-04", new BigDecimal("17.97"), SongCategory.ALTERNATIVE),
            new Song(10L, "Hysteria", "Def Leppard", "1987-06-20", new BigDecimal("17.97"), SongCategory.ROCK),
            new Song(11L, "A Life of Saturdays", "Dexter Freebish", "2000-12-06", new BigDecimal("16.97"), SongCategory.RAP),
            new Song(12L, "Human Clay", "Creed", "1999-10-21", new BigDecimal("18.97"), SongCategory.ROCK),
            new Song(13L, "My, I'm Large", "Bobs", "1987-02-20", new BigDecimal("11.97"), SongCategory.COUNTRY),
            new Song(14L, "So", "Peter Gabriel", "1986-10-03", new BigDecimal("17.97"), SongCategory.POP),
            new Song(15L, "Big Ones", "Aerosmith", "1994-05-08", new BigDecimal("18.97"), SongCategory.ROCK),
            new Song(16L, "90125", "Yes", "1983-10-16", new BigDecimal("11.97"), SongCategory.ROCK),
            new Song(17L, "1984", "Van Halen", "1984-08-19", new BigDecimal("11.97"), SongCategory.ROCK),
            new Song(18L, "Escape", "Journey", "1981-02-25", new BigDecimal("11.97"), SongCategory.CLASSIC_ROCK)
    ));

    private Integer maxSearchResults = 30;

    public Integer getMaxSearchResults() {
        return maxSearchResults;
    }

    public void setMaxSearchResults(Integer maxSearchResults) {
        this.maxSearchResults = maxSearchResults;
    }

    @Override
    public Song findOne(Long id) {
        Optional<Song> songOptional = catalogData.stream()
                .filter(song -> song.getId().equals(id))
                .findAny();

        return songOptional.get();
    }

    @Override
    public Collection<Song> findByArtistContainingOrTitleContainingAllIgnoreCase(String artist, String title) {

        String artistLow = artist.toLowerCase();
        String titleLow = title.toLowerCase();

        return catalogData.stream()
                .filter(song -> song.getTitle().toLowerCase().contains(titleLow) ||
                        song.getArtist().toLowerCase().contains(artistLow))
                .collect(Collectors.collectingAndThen(Collectors.toList(), list -> list.size() <= maxSearchResults ? list : list.subList(0, maxSearchResults)));
    }

    @Override
    public Collection<Song> findBySongCategory(SongCategory category) {
        return catalogData.stream()
                .filter(song -> song.getSongCategory() == category)
                .collect(Collectors.collectingAndThen(Collectors.toList(), list -> list.size() <= maxSearchResults ? list : list.subList(0, maxSearchResults)));
    }

    public Collection<Song> findAll() {
        return Collections.unmodifiableCollection(catalogData);
    }

    @Override
    public long count() {
        return catalogData.size();
    }

    @Override
    public Song save(Song song) {
        song.setId(new Random().nextLong());
        catalogData.add(song);
        return song;
    }

    @Override
    public void delete(Song song) {
        for (Song s : catalogData) {
            if (s.getId() == song.getId()) {
                catalogData.remove(s);
                break;
            }

        }
    }

}
