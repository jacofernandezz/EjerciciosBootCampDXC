package com.bananaapps.bananamusic.controller;

import com.bananaapps.bananamusic.domain.music.PurchaseOrderLineSong;
import com.bananaapps.bananamusic.service.music.ShoppingCart;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/cart")
@Validated
@Tag(name = "API del carrito", description = "Endpoints para manejar el carrito")
public class CartController {

    @Autowired
    ShoppingCart shoppingCart;

    @GetMapping("/balance")
    @Operation(summary = "Para recuperar el balance", description = "Devuelve el importe total del carrito")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cuando el balance se recupera correctamente.")
    })
    public ResponseEntity<Double> getBalance() {
        return ResponseEntity.ok(shoppingCart.getBalance());
    }

    @PutMapping
    @Operation(summary = "Para añadir un item", description = "Devuelve un mensaje de confirmación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Cuando el item se añade correctamente."),
            @ApiResponse(responseCode = "412", description = "Cuando hay un error en alguna de las precondiciones.")
    })
    public ResponseEntity<String> addItem (
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del item", required = true,
                    content = @Content(schema = @Schema(implementation = PurchaseOrderLineSong.class)))
            @RequestBody @Valid PurchaseOrderLineSong item) {
        shoppingCart.addItem(item);
        return new ResponseEntity<>("Item añadido con éxito!", HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{lineNumber}")
    @Operation(summary = "Para eliminar un item", description = "Devuelve un mensaje de confirmación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Cuando el item se elimina correctamente."),
            @ApiResponse(responseCode = "404", description = "Cuando no hay ningún item con el identificador dado."),
            @ApiResponse(responseCode = "412", description = "Cuando hay un error en alguna de las precondiciones.")
    })
    public ResponseEntity<String> removeItem (
            @Parameter(description = "El identificador del item a eliminar.", example = "1")
            @PathVariable @Min(1) Long lineNumber) {
        shoppingCart.removeItem(lineNumber);
        return new ResponseEntity<>("Item eliminado con éxito!", HttpStatus.ACCEPTED);
    }

    @GetMapping("/item-count")
    @Operation(summary = "Para recuperar el número de items", description = "Devuelve la cantidad total de items en el carrito")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cuando la cantidad se recupera correctamente.")
    })
    public ResponseEntity<Integer> getItemCount() {
        return ResponseEntity.ok(shoppingCart.getItemCount());
    }

    @DeleteMapping("/empty")
    @Operation(summary = "Para vaciar el carrito", description = "Elimina todos los items del carrito y devuelve un mensaje de confirmación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Cuando el carrito se vacia correctamente.")
    })
    public ResponseEntity<String> empty() {
        shoppingCart.empty();
        return new ResponseEntity<>("Carrito vaciado con éxito!", HttpStatus.ACCEPTED);
    }

    @PostMapping("/buy")
    @Operation(summary = "Para ejecutar la orden de compra", description = "Devuelve un mensaje de confirmación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Cuando la compra se ejecuta correctamente."),
            @ApiResponse(responseCode = "404", description = "Cuando no se encuentra ningún item en el carrito.")
    })
    public ResponseEntity<String> buy() {
        shoppingCart.buy();
        return new ResponseEntity<>("Orden de compra ejecutada con éxito!", HttpStatus.ACCEPTED);
    }

}
