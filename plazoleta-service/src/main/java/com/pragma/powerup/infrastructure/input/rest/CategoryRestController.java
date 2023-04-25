package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.CategoryRequestDto;
import com.pragma.powerup.application.dto.response.CategoryResponseDto;
import com.pragma.powerup.application.handler.ICategoryHandler;
import com.pragma.powerup.application.handler.impl.CategoryHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryRestController {

    private final ICategoryHandler categoryHandler;

    @Operation(summary = "Add a new category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category created", content = @Content),
            @ApiResponse(responseCode = "409", description = "Category already exists", content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<Void> saveCategory(@Valid @RequestBody CategoryRequestDto category) {
        categoryHandler.saveCategory(category);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Get all categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All categories returned",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CategoryResponseDto.class)))),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content)
    })
    @GetMapping("/")
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories() {
        return ResponseEntity.ok(categoryHandler.getAllCategories());
    }

    @Operation(summary = "Get category by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category returned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoryResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Category no found",
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable(value = "id") Long categoryId) {
        return ResponseEntity.ok(categoryHandler.getCategoryById(categoryId));
    }


    @Operation(summary = "Detele a category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Category not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deteteCategoryById(@PathVariable(value = "id") Long categoryId) {
        categoryHandler.deleteCategoryById(categoryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
