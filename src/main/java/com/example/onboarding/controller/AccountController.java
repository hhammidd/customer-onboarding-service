package com.example.onboarding.controller;

import com.example.onboarding.model.Account;
import com.example.onboarding.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
@Tag(name = "Accounts", description = "API for managing user accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

//    @GetMapping("/iban/{iban}")
//    @Operation(summary = "Retrieve an Account by IBAN", description = "Returns a single account details by IBAN number.")
//    @ApiResponse(responseCode = "200", description = "Account found and returned", content = @Content(schema = @Schema(implementation = Account.class)))
//    @ApiResponse(responseCode = "404", description = "Account not found")
//    public ResponseEntity<Account> getAccountByIban(@PathVariable String iban) {
//        return accountService.getAccountByIban(iban)
//                .map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }

    @GetMapping("/customer/{customerId}")
    @Operation(summary = "Retrieve Account by Customer ID", description = "Returns account details associated with a specific customer ID.")
    @ApiResponse(responseCode = "200", description = "Account found and returned", content = @Content(schema = @Schema(implementation = Account.class)))
    @ApiResponse(responseCode = "404", description = "Account not found")
    public ResponseEntity<Account> getAccountByCustomerId(@PathVariable Long customerId) {
        return accountService.getAccountByCustomerId(customerId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    @Operation(summary = "Create or Update an Account", description = "Creates a new account or updates an existing account in the database.")
    @ApiResponse(responseCode = "200", description = "Account successfully created/updated", content = @Content(schema = @Schema(implementation = Account.class)))
    @ApiResponse(responseCode = "400", description = "Invalid account data provided")
    public ResponseEntity<Account> saveAccount(@RequestBody Account account) {
        Account savedAccount = accountService.saveAccount(account);
        return ResponseEntity.ok(savedAccount);
    }
}