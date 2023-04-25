package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.SmsMessageRequestDto;
import com.pragma.powerup.application.handler.ISmsMessageHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/sms/message")
@RequiredArgsConstructor
public class SmsMessageRestController {

    private final ISmsMessageHandler smsMessageHandler;


    @Operation(summary = "Send a new sms message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sms message created and sent ", content = @Content),
            @ApiResponse(responseCode = "400", description = "Error sending sms message", content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<Void> sendSmsMessage(@Valid @RequestBody SmsMessageRequestDto smsMessageRequestDto) {
        smsMessageHandler.sendSmsMessage(smsMessageRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
