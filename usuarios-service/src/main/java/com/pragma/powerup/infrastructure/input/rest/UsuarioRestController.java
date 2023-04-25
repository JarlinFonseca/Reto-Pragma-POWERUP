package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.UsuarioRequestDto;
import com.pragma.powerup.application.dto.response.UsuarioResponseDto;
import com.pragma.powerup.application.handler.IUsuarioHandler;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UsuarioRestController {

    private final IUsuarioHandler usuarioHandler;


    @Operation(summary = "Add a new owner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Owner created", content = @Content),
            @ApiResponse(responseCode = "409", description = "Owner already exists", content = @Content)
    })
    @PostMapping("/owner")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> saveOwner(@Valid @RequestBody UsuarioRequestDto propietario) {
        usuarioHandler.saveUser(propietario);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @Operation(summary = "Add a new employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Employee created", content = @Content),
            @ApiResponse(responseCode = "409", description = "Employee already exists", content = @Content)
    })
    @PostMapping("/employee")
    @PreAuthorize("hasAuthority('PROPIETARIO')")
    public ResponseEntity<Void> saveEmployee(@Valid @RequestBody UsuarioRequestDto empleado) {
        usuarioHandler.saveUser(empleado);
        usuarioHandler.saveRestaurantEmployee(empleado);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Add a new client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Client created", content = @Content),
            @ApiResponse(responseCode = "409", description = "Client already exists", content = @Content)
    })
    @PostMapping("/client")
    public ResponseEntity<Void> saveClient(@Valid @RequestBody UsuarioRequestDto cliente) {
        usuarioHandler.saveUser(cliente);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @Operation(summary = "Add a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created", content = @Content),
            @ApiResponse(responseCode = "409", description = "User already exists", content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<Void> saveUser(@Valid @RequestBody UsuarioRequestDto usuarioRequestDto){
        usuarioHandler.saveUser(usuarioRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All users returned",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UsuarioResponseDto.class)))),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content)
    })
    @GetMapping("/")
    public ResponseEntity<List<UsuarioResponseDto>> getAllUsers(){
        return ResponseEntity.ok(usuarioHandler.getAllUsers());
    }

    @Operation(summary = "Get a user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User returned", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
   @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> getUserById(@PathVariable(value = "id") Long usuarioId) {
        return  ResponseEntity.ok(usuarioHandler.getUserById(usuarioId));
    }

    @Operation(summary = "Get a user by email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User returned", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioResponseDto> getUserByCorreo(@PathVariable(value = "email") String correo){
        return  ResponseEntity.ok((usuarioHandler.getUserByCorreo(correo)));
    }

    @Operation(summary = "Exist a user by ID (Boolean)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User returned", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @GetMapping("existsUserById/{id}")
    public ResponseEntity<Boolean> existsUserById(@PathVariable(value = "id") Long usuarioId){
        return  ResponseEntity.ok(usuarioHandler.existsUserById(usuarioId));
    }


    @Operation(summary = "Detele a user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deteteUserById(@PathVariable(value = "id")Long usuarioId){
        usuarioHandler.deleteUserById(usuarioId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
