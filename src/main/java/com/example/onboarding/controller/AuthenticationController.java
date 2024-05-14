package com.example.onboarding.controller;

import com.example.onboarding.service.AuthenticationService;
import com.example.onboarding.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<String> generateToken(@RequestParam String username, @RequestParam String password) {
        if (authenticationService.authenticate(username, password)) {
            String token = JwtUtil.generateToken(username);
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    //    @PostMapping("/logon")
    //    @Operation(summary = "Log in using a token",
    //            description = "Verifies the provided token's validity and returns a success message if valid.",
    //            responses = {
    //                    @ApiResponse(responseCode = "200", description = "Token verification successful"),
    //                    @ApiResponse(responseCode = "401", description = "Invalid token")
    //            })
    //    public ResponseEntity<String> logon(@RequestHeader("Authorization") String token) {
    //        boolean isValid = authenticationService.verifyToken(token);
    //        if (isValid) {
    //            return ResponseEntity.ok("User successfully authenticated.");
    //        } else {
    //            return ResponseEntity.status(401).body("Invalid token");
    //        }
    //    }

    @PostMapping("/logon")
    @Operation(summary = "Log in using a token",
            description = "Verifies the provided token's validity and returns a success message if valid.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Token verification successful"),
                    @ApiResponse(responseCode = "401", description = "Invalid token")
            })
    public ResponseEntity<String> logon(@RequestHeader(value = "Authorization", required = false) String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Remove "Bearer " prefix
        }

        try {
            // Assuming JwtUtil has a method to validate the token
            if (JwtUtil.validateToken(token)) {
                return ResponseEntity.ok("Success response"); // Or any other success message or data
            } else {
                return ResponseEntity.status(401).body("Invalid token");
            }
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid token");
        }
    }

    // Placeholder for actual authentication logic
    private boolean authenticate(String username, String password) {
        // Implement authentication logic here
        return "admin".equals(username) && "password".equals(password);
    }
}