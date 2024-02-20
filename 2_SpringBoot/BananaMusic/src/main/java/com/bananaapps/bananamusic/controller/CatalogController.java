package com.bananaapps.bananamusic.controller;

import com.bananaapps.bananamusic.domain.music.Song;
import com.bananaapps.bananamusic.service.music.Catalog;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;

@RestController
@RequestMapping("/catalog")
@Validated
@Tag(name = "API del catálogo", description = "Endpoints para manejar el catálogo")
public class CatalogController {

    @Autowired
    Catalog catalog;

    @GetMapping("/{id}")
    @Operation(summary = "Para recupera una canción por su identificadoror", description = "Devuelve la canción")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cuando la canción se devuelve correctamente."),
            @ApiResponse(responseCode = "404", description = "Cuando no se encuentra la canción."),
            @ApiResponse(responseCode = "412", description = "Cuando hay un error en alguna de las precondiciones.")

    })
    public ResponseEntity<Song> getSongById(
            @Parameter(description = "El identificador del item a consultar.", example = "1")
            @PathVariable @Min(1) Long id) {
        return new ResponseEntity<>(catalog.findById(id), HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Para recuperar una canción filtrando por una palabra clave", description = "Devuelve una lista de canciones")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cuando las canciones se devuelve correctamente."),
            @ApiResponse(responseCode = "412", description = "Cuando hay un error en alguna de las precondiciones.")
    })
    public ResponseEntity<Collection<Song>> getSongsByKeywords(
            @Parameter(description = "Palabra clave para filtrar", example = "and")
            @NotBlank(message = "El parámetro 'keyword' no puede estar vacío") @Size(min = 3, max = 50, message = "El tamaño de 'keyword' debe estar entre 3 y 50 caracteres")
            @RequestParam String keyword) {
        return new ResponseEntity<>(catalog.findByKeyword(keyword), HttpStatus.OK);
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Para añadir una nueva canción", description = "Devuelve la canción creada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cuando la canción se crea correctamente."),
            @ApiResponse(responseCode = "412", description = "Cuando hay un error en alguna de las precondiciones.")
    })
    public ResponseEntity<Song> createSong(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la canción", required = true,
                    content = @Content(schema = @Schema(implementation = Song.class)))
            @RequestBody @Valid Song song) {
        return new ResponseEntity<>(catalog.save(song), HttpStatus.CREATED);
    }

    @PutMapping
    @Operation(summary = "Para crear o actualizar varias canciones", description = "Crea cada una de las canciones que se le pasa y si ya existe la actualiza.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Cuando las canciones se crean/actualizan correctamente."),
            @ApiResponse(responseCode = "412", description = "Cuando hay un error en alguna de las precondiciones.")
    })
    public ResponseEntity<Void> createSongs(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Lista de canciones para crear o actualizar", required = true)
            @RequestBody @Valid Collection<Song> songs) {
        catalog.saveCollection(songs);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

}
