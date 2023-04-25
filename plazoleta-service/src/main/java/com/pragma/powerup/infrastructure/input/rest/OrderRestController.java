package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.OrderRequestDto;
import com.pragma.powerup.application.dto.response.OrderResponseDto;
import com.pragma.powerup.application.handler.IOrderHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderRestController {

    private final IOrderHandler orderHandler;

    @Operation(summary = "Add a new order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created", content = @Content),
            @ApiResponse(responseCode = "409", description = "Order already exists", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "403", description = "No authorized", content = @Content)
    })
    @PostMapping("/placeAnOrder")
    @PreAuthorize("hasAuthority('CLIENTE')")
    public ResponseEntity<OrderResponseDto> placeAnOrder(@Validated @RequestBody OrderRequestDto orderRequest) {
        orderHandler.saveOrder(orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Get Orders By State")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Orders found", content = @Content),
            @ApiResponse(responseCode = "404", description = "Orders don't exists", content = @Content),
            @ApiResponse(responseCode = "400", description = "bad request", content = @Content),
            @ApiResponse(responseCode = "403", description = "No authorized", content = @Content)
    })
    @GetMapping("/getOrdersByStatePaginated/page/{page}/size/{size}/status/{estado}")
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public ResponseEntity<List<OrderResponseDto>> getAllOrderByState(@PathVariable Integer page, @PathVariable Integer size, @PathVariable(value = "estado") String estado) {
        if (size <= 0L || page < 0L || estado.isBlank() || estado.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        //ResponseEntity.status(HttpStatus.OK).body(orderHandler.getAllOrdersWithPagination(page,size, estado));
        return ResponseEntity.ok(orderHandler.getAllOrdersWithPagination(page, size, estado));
    }

    @Operation(summary = "Take order and update status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order taken", content = @Content),
            @ApiResponse(responseCode = "409", description = "Order doesn't exists", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "403", description = "No authorized", content = @Content)
    })
    @PutMapping("/takeOrderAndUpdateStatus/{idOrder}/status/{status}")
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public ResponseEntity<Void> takeOrderAndUpdateStatus(@PathVariable Long idOrder, @PathVariable String status) {
        if (idOrder <= 0L || status.isBlank() || status.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        orderHandler.takeOrderAndUpdateStatus(idOrder, status);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Order ready")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order ready", content = @Content),
            @ApiResponse(responseCode = "409", description = "Order doesn't exists", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "403", description = "No authorized", content = @Content)
    })
    @PutMapping("/updateAndNotifyOrderReady/{idOrder}")
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public ResponseEntity<Void> updateAndNotifyOrderReady(@PathVariable Long idOrder) {
        if (idOrder <= 0L) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        orderHandler.updateAndNotifyOrderReady(idOrder);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Order deliver")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order deliver", content = @Content),
            @ApiResponse(responseCode = "409", description = "Order doesn't exists", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "403", description = "No authorized", content = @Content)
    })
    @PutMapping("/orderDeliver/{idOrder}/pin/{pin}")
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public ResponseEntity<Void> orderDeliver(@PathVariable Long idOrder, @PathVariable String pin) {
        if (idOrder <= 0L) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        orderHandler.deliverOrder(idOrder, pin);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "Cancel Order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order cancel", content = @Content),
            @ApiResponse(responseCode = "409", description = "Order doesn't exists", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "403", description = "No authorized", content = @Content)
    })
    @PutMapping("/cancelOrder/{idOrder}")
    @PreAuthorize("hasAuthority('CLIENTE')")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long idOrder){
        if(idOrder <= 0L)  return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        orderHandler.cancelOrder(idOrder);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
