package com.example.onboarding.controller;

import com.example.onboarding.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/token")
    @Operation(summary = "Retrieve an authentication token",
            description = "Authenticates a user with a username and password and returns a token which can be used for accessing secured endpoints.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Authentication successful, token provided",
                            content = @Content(schema = @Schema(type = "string", format = "uuid", description = "Bearer token"))),
                    @ApiResponse(responseCode = "401", description = "Authentication failed")
            })
    public ResponseEntity<String> getToken(@RequestParam String username, @RequestParam String password) {
        String token = authenticationService.authenticate(username, password);
        if (token != null) {
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(401).body("Authentication failed");
        }
    }

    @PostMapping("/logon")
    @Operation(summary = "Log in using a token",
            description = "Verifies the provided token's validity and returns a success message if valid.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Token verification successful"),
                    @ApiResponse(responseCode = "401", description = "Invalid token")
            })
    public ResponseEntity<String> logon(@RequestHeader("Authorization") String token) {
        boolean isValid = authenticationService.verifyToken(token);
        if (isValid) {
            return ResponseEntity.ok("User successfully authenticated.");
        } else {
            return ResponseEntity.status(401).body("Invalid token");
        }
    }
}