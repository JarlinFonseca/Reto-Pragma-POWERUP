package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.DishRequestDto;
import com.pragma.powerup.application.dto.request.DishUpdateRequestDto;
import com.pragma.powerup.application.dto.response.DishResponseDto;
import com.pragma.powerup.application.handler.IDishHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/dish")
@RequiredArgsConstructor
public class DishRestController {

    private final IDishHandler dishHandler;

    @Operation(summary = "Add a new dish")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dish created", content = @Content),
            @ApiResponse(responseCode = "409", description = "Dish already exists", content = @Content)
    })
    @PostMapping("/")
    @PreAuthorize("hasAuthority('PROPIETARIO')")
    public ResponseEntity<Void> saveDish(@Valid @RequestBody DishRequestDto dish) {
        dishHandler.saveDish(dish);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Update dish by Id, RequestBody price and description")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dish updated",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = DishRequestDto.class)))),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content)
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('PROPIETARIO')")
    public ResponseEntity<DishRequestDto> updateDish(@PathVariable(value = "id")Long dishId, @Valid @RequestBody DishUpdateRequestDto dishUpdateRequestDto){
        dishHandler.updateDish(dishId, dishUpdateRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @Operation(summary = "Enable or disable dish")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dish enable/disable",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = DishRequestDto.class)))),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content)
    })
    @PutMapping("/{id}/activate/{enableDisable}")
    @PreAuthorize("hasAuthority('PROPIETARIO')")
    public ResponseEntity<DishRequestDto> updateEnableDisableDish(@PathVariable(value = "id")Long dishId, @PathVariable(value = "enableDisable")Long enableDisable){
       dishHandler.updateEnableDisableDish(dishId, enableDisable);
        return new ResponseEntity<>(HttpStatus.OK);

    }


    @Operation(summary = "Get all dishes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All dishes returned",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = DishResponseDto.class)))),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content)
    })
    @GetMapping("/")
    public ResponseEntity<List<DishResponseDto>> getAllDishes() {
        return ResponseEntity.ok(dishHandler.getAllDishes());
    }

    @Operation(summary = "Get all dishes by restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All dishes returned",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = DishResponseDto.class)))),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content)
    })
    @GetMapping("/restaurant/{idRestaurante}/page/{page}/size/{size}")
    @PreAuthorize("hasAuthority('CLIENTE')")
    public ResponseEntity<List<DishResponseDto>> getAllDishesByRestaurant(@PathVariable(value = "idRestaurante" ) Long idRestaurante,@PathVariable(value = "page" )Integer page, @PathVariable(value = "size") Integer size) {
        return ResponseEntity.ok(dishHandler.findAllByRestauranteId(idRestaurante, page,size));
    }


    @Operation(summary = "Get dish by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dish returned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DishResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Dish no found",
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<DishResponseDto> getDishById(@PathVariable(value = "id") Long dishId) {
        return ResponseEntity.ok(dishHandler.getDishById(dishId));
    }


    @Operation(summary = "Detele a dish")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dish deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Dish not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deteteDishById(@PathVariable(value = "id") Long dishId) {
        dishHandler.deleleDishById(dishId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
